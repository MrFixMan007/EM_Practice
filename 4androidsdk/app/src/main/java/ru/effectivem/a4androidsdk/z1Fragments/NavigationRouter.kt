package ru.effectivem.a4androidsdk.z1Fragments

interface NavigationRouter {
    val currentPosition: Int
    fun openNext()
    fun openPrevious()
}
