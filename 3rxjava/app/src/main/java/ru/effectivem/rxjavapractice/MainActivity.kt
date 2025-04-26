package ru.effectivem.rxjavapractice

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.effectivem.rxjavapractice.data.api.ApiService
import ru.effectivem.rxjavapractice.data.api.MockDiscountService
import ru.effectivem.rxjavapractice.domain.DiscountCard
import ru.effectivem.rxjavapractice.domain.Joke
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var mockDiscountApi1: MockDiscountService
    private lateinit var mockDiscountApi2: MockDiscountService

    private lateinit var button: Button
    private lateinit var textView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var editText: EditText
    private lateinit var discountRequestButton1: Button
    private lateinit var discountRequestButton2: Button

    private lateinit var disposeBag: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        init()
        initApi()
        setupUi()
        setupTimer()
        setupObserverInput()
        schedulers()
    }

    private fun init() {
        disposeBag = CompositeDisposable()
        val sub = CompositeDisposable()
    }

    private fun initApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // API
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
        mockDiscountApi1 = MockDiscountService("As")
        mockDiscountApi2 = MockDiscountService("Bs")
    }

    private fun setupUi() {
        button = findViewById(R.id.buttonFetch)
        button.setOnClickListener { fetchPost() }

        textView = findViewById(R.id.textViewResult)
        timerTextView = findViewById(R.id.textViewTimer)
        editText = findViewById(R.id.editTextInput)

        discountRequestButton1 = findViewById(R.id.apiRequest1)
        discountRequestButton1.setOnClickListener { fetchDiscountCards() }

        discountRequestButton2 = findViewById(R.id.apiRequest2)
        discountRequestButton2.setOnClickListener { fetchDiscountCardsOrNull() }
    }

    private fun fetchPost() { // Сделайте сетевой запрос и отобразите результат на экране
        disposeBag.add(apiService.getPost()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Joke(it.title)
            }
            .subscribe(
                {
                    textView.text = it.text
                },
                {
                    textView.text = applicationContext.getText(R.string.result_tip)
                    toast("Ошибка с запросом ${it.message}")
                }
            )
        )
    }

    private fun setupTimer() { // Сделайте таймер. TextView которая раз в секунду меняется
        disposeBag.add(Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                timerTextView.text = "$it"
            }
        )
    }

    private fun setupObserverInput() { // Сделайте EditText. При наборе текста выводите в лог
        // содержимое EditText всегда, когда пользователь 3 секунды что-то не вводил (debounce)
        disposeBag.add(RxTextView.textChanges(editText)
            .skipInitialValue()
            .debounce(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                if (text.isNotEmpty()) {
                    Log.d("EditText", "Пользователь ввёл: $text")
                    toast("${text.trim()}")
                }
            }
        )
    }

    private fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.dispose()
    }

    private fun fetchDiscountCards() { //а) Если 1 из запросов падает, то все равно выводить
        // (найти метод RX для такого, чтоб самому не прописывать логику)
        disposeBag.add(Observable.zip(
            mockDiscountApi1.getDiscountCards()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    toast("${it.message}")
                }.onErrorReturn {
                    emptyList()
                },
            mockDiscountApi2.getDiscountCards()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    toast("${it.message}")
                }.onErrorReturn {
                    emptyList()
                }
        ) { list1, list2 -> list1 + list2 }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.map { cardResponse ->
                    DiscountCard(
                        name = cardResponse.name,
                        discountSize = cardResponse.discountSize,
                    )
                }
            }
            .subscribe { result ->
                textView.text = if (result.isNullOrEmpty()) "" else result.toString()
            }
        )
    }

    private fun fetchDiscountCardsOrNull() { // б) Если 1 из запросов падает, то не выводить ничего (найти метод RX)
        disposeBag.add(Observable.zip(
            mockDiscountApi1.getDiscountCards(),
            mockDiscountApi2.getDiscountCards()
        ) { list1, list2 -> list1 + list2 }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.map { cardResponse ->
                    DiscountCard(
                        name = cardResponse.name,
                        discountSize = cardResponse.discountSize,
                    )
                }
            }
            .subscribe(
                { result -> textView.text = result.toString() },
                { error ->
                    toast("🔥 ${error.message}")
                    textView.text = ""
                }
            )
        )
    }

    private fun schedulers() {
        // RxCachedThreadScheduler Schedulers.io()
        // RxNewThreadScheduler Schedulers.newThread()
        // RxSingleScheduler Schedulers.single()
        // RxComputationThreadPool Schedulers.computation()

        // Запускается на Schedulers.newThread(), создавая новый поток RxNewThreadScheduler-1.
        // Таймер ждет 10 мс и затем эмитит значение.
        disposeBag.add(Observable.timer(10, TimeUnit.MILLISECONDS, Schedulers.newThread())
            // subscribeOn(Schedulers.io()) здесь ничего не меняет, так как
            // subscribeOn влияет только на место подписки, а таймер уже был запущен на newThread.
            .subscribeOn(Schedulers.io())
            .map {
                // Выполняется на том же потоке, где был создан Observable.timer(), то есть RxNewThreadScheduler-1.
                Log.d("HAHAHA", "mapThread = ${Thread.currentThread().name}")
            }
            // Вызывается во время подписки, но с учетом последнего subscribeOn(Schedulers.computation()),
            // подписка происходит на RxComputationThreadPool-1.
            .doOnSubscribe {
                Log.d("HAHAHA", "onSubscribeThread = ${Thread.currentThread().name}")
            }
            // повлиял на doOnSubscribe
            .subscribeOn(Schedulers.computation())
            .retryUntil {
                true
            }
            .retryWhen { err ->
                if (err.take(1) == null) Completable.create {
                    it.onComplete()
                }.toObservable<Int>() else Maybe.just(0).toObservable()
            }
            // Переключает поток для всех следующих операторов на RxSingleScheduler-1.
            .observeOn(Schedulers.single())
            .flatMap {
                Log.d("HAHAHA", "flatMapThread = ${Thread.currentThread().name}")
                // Переключает поток на RxCachedThreadScheduler
                Observable.just(it)
                    .subscribeOn(Schedulers.io())
            }
            // subscribe вызывается в потоке обработки flatMap, который всё ещё RxSingleScheduler-1,
            // но поскольку Observable.just(it).subscribeOn(Schedulers.io()) может перенаправить subscribe,
            // результат оказывается на RxCachedThreadScheduler-1.
            .subscribe {
                Log.d("HAHAHA", "subscribeThread = ${Thread.currentThread().name}")
            }
        )

        val a = Flowable.create(
            {
                it.onNext(5)
            },
            BackpressureStrategy.BUFFER
        )

        disposeBag.add(
            Observable.just(10, 20, 30)
                .onErrorResumeNext { e: Throwable ->
                    Observable.just(10, 20, 30)
                }
                .subscribe { println("✅ Item: $it") }
        )

        val obs = create()

        val publish = obs.concatMap {
            Observable.just(it)
        }.publish().refCount()

        publish.subscribe {
            println("11 $it")
        }


        publish.subscribe {
            println("22 $it")
        }
//
        // Создание PublishSubject
//        val publishSubject = PublishSubject.create<Int>()
//
//        // Подписка на PublishSubject
//        val disposable = publishSubject.subscribe(
//            // onNext - обработка нового значения
//            { value -> println("Получено значение: $value") },
//            // onError - обработка ошибки
//            { error -> println("Ошибка: ${error.message}") },
//            // onComplete - обработка завершения последовательности
//            { println("Последовательность завершена") }
//        )
//
//        val singlke = Maybe.create<Int> {
//            it.onSuccess(5)
//            it.onComplete()
//        }
//
//        // Эмитирование событий
//        publishSubject.onNext(1)
//        publishSubject.onNext(2)
//        publishSubject.onNext(3)
//
//        // Завершение последовательности
//        publishSubject.onComplete()
//
//        // Отписка от PublishSubject
//        disposable.dispose()
//
//
//        val userInput = Observable.just(
//            "100", "150", "200",
//            "300", "400", "500"
//        )
//            .timestamp()
//            .concatMap {
//                Observable.just(it).delay(it.value().toLong(), TimeUnit.MILLISECONDS)
//            } // эмуляция "ввода"

//        disposeBag.add(
//
//            userInput
//                .switchMap { query -> fakeApiCall("${query.value()} ${query.time()}") }
//                // отменяет предыдущий запрос при новом вводе
//                .subscribe { result -> println("Search result: $result") }
//
//        )
//
//        Flowable.create()

        val flo = ReplaySubject.createWithTimeAndSize<Int>( )

        val flowable = Observable.interval(1, TimeUnit.SECONDS)
            .replay()
    }
}

fun fakeApiCall(query: String): Observable<String> {
    return Observable.just("Results for: $query")
        .subscribeOn(Schedulers.)
        .delay(200, TimeUnit.MILLISECONDS) // запрос как будто идёт 1 секунду
}

fun main() {

    val bag = CompositeDisposable()

    bag.addAll(

        Observable.interval(1000, TimeUnit.MILLISECONDS).map { it.toInt() }.subscribe {
            println("1 $it")
        },

        create().subscribe({
            println(it)
        },
            {

            },
            {

            }
        )

    )

}

fun create(): Observable<Int> = Observable.create { emitter ->
    with(emitter) {
        repeat(10) {
            onNext(it)
        }
        onComplete()
    }
}


fun create1() = Observable.timer(2000, TimeUnit.MILLISECONDS).map { it.toInt() }.forEach {
    println("1 $it")
}

fun flowable(): Flowable<Int> = create().toFlowable(BackpressureStrategy.BUFFER)


fun create2() = Observable.defer {
    Observable.just(5)
}