package ru.effectivem.a4androidsdk.z1Fragments

import androidx.fragment.app.Fragment

interface NavigationRouter {
    val currentPosition: Int
    fun openNext()
    fun openPrevious()
    fun replace(fragment: Fragment)
}
