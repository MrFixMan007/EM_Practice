package ru.effectivem.kotlinpractice

import android.app.Application
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.effectivem.kotlinpractice.z2.LaunchTimeDelegateSingle

class MyApp : Application() {

    private val coroutineScope = CoroutineScope((Dispatchers.IO))

    private val launchTime: Long by LaunchTimeDelegateSingle.getInstance(this)
    private val launchTime1: Long by LaunchTimeDelegateSingle.getInstance(this)

    override fun onCreate() {
        super.onCreate()

        coroutineScope.launch {
            while (isActive) {
                Log.d("launchTime", "$launchTime")
                delay(3000)
                Log.d("launchTime1", "$launchTime1")
            }
        }
    }
}