package ru.effectivem.kotlinpractice.rxJava

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.withContext

fun main(): Unit {
    var count = 0
    val co = Observable.create { emitter ->
        repeat(5) {
            emitter.onNext(it)
            count++
        }
        emitter.onComplete()
    }.observeOn(Schedulers.single())

    val flowable = Flowable.just(11, 13, 15)

    val disposable = CompositeDisposable()
    val cop = co.doOnComplete {
        println()
    }.subscribeOn(Schedulers.io()).subscribe {
        println("2 $it")
    }

    disposable.addAll(
        co.doOnComplete {
            println()
        }.subscribe {
            println("1 $it")
        },
        cop,
        co.map {
            it * 100
        }.doOnComplete {
            println()
        }.subscribe {
            println("3 $it")
        },
        flowable.subscribe {
            println("flowable $it")
        },
        Single.just(1).subscribe(
            { println("$it from single") },
            { println("onError") }
        )
    )

    disposable.add(
        set()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    println("onComplete")
                }

                override fun onError(e: Throwable) {
                    println("OnError")
                }
            })
    )

    disposable.dispose()

    println(disposable.isDisposed)
    println(count)
}

fun set(): Completable {
    return Completable.create { emitter ->
        println("lol")
        emitter.onComplete()
    }
}