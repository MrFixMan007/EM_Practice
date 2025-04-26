package ru.effectivem.a4androidsdk.z1Fragments

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MyNavigationRouter(
    private val fragmentManager: FragmentManager,
    @IdRes private val containerViewId: Int,
    private val fragments: List<Fragment>,
    startPosition: Int? = null,
) : NavigationRouter {

    private var _currentPosition: Int = startPosition ?: 0
    override val currentPosition: Int
        get() = _currentPosition

    init {
        replace(_currentPosition)
    }

    override fun openNext() {
        _currentPosition = if (_currentPosition < fragments.size - 1) _currentPosition + 1 else 0
        replace(_currentPosition)
    }

    override fun openPrevious() {
        _currentPosition = if (_currentPosition > 0) _currentPosition - 1 else fragments.size - 1
        replace(_currentPosition)
    }

    private fun replace(position: Int) {
        if (position > fragments.size - 1) throw IllegalArgumentException("index must be < fragments size, but fragments size = ${fragments.size} and index = $position")
        fragmentManager.beginTransaction()
            .replace(containerViewId, fragments[position])
            .addToBackStack(null)
            .commit()
    }
}