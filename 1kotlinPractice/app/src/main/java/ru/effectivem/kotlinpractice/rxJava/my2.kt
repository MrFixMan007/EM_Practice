package ru.effectivem.kotlinpractice.rxJava

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

fun main() {
    val obs = Observable.create { emitter ->
        for (i in 1..50) {
            emitter.onNext("Hy $i times")
        }
        emitter.onNext("Good by")
        emitter.onComplete()
    }.observeOn(Schedulers.io(), true).buffer(5)

    val disposable = CompositeDisposable()

    disposable.add(obs
        .subscribeOn(Schedulers.trampoline())
        .flatMap {
            Observable.create { emitter ->
                for (item in it) {
                    emitter.onNext("$item new")
                }
            }
        }
        .subscribe(
            {
                println(it)
            },
            {
                println("Error")
            }
        )
    )


}