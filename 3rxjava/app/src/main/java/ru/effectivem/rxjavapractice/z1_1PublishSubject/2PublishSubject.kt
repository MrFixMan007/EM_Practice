package ru.effectivem.rxjavapractice.z1_1PublishSubject

import io.reactivex.subjects.PublishSubject

fun main() {

    val subject = PublishSubject.create<String>()

    subject.subscribe { println(it) } // Подписка до отправки значений

    subject.onNext("1")
    subject.onNext("2")
    subject.onNext("3")

}