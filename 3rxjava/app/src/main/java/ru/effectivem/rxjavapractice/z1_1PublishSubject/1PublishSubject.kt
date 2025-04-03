package ru.effectivem.rxjavapractice.z1_1PublishSubject

import android.util.Log
import io.reactivex.subjects.PublishSubject

fun main() {

    // PublishSubject не хранит прошлые значения.
    val subject = PublishSubject.create<String>()

    subject.onNext("1")
    subject.onNext("2")
    subject.onNext("3")

    //Поскольку subscribe() вызывается после всех onNext(), подписчик не увидит уже отправленные значения.
    //Лог будет пустым
    subject.subscribe { Log.d("TAG", it) }

}