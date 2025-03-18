package ru.effectivem.kotlinpractice.z2

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LaunchTimeDelegateSingle private constructor(context: Context) : ReadOnlyProperty<Any?, Long> {

    private val keyName = "launchTime"

    private var _isSettledInThisSession = false
    private val isSettledInThisSession get() = _isSettledInThisSession

    private val prefs: SharedPreferences by lazy {
        context.applicationContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Long {
        if (!_isSettledInThisSession) {
            val isSuccess = prefs.edit().putLong(keyName, System.currentTimeMillis()).commit()
            if (isSuccess) _isSettledInThisSession = true
        }
        return prefs.getLong(keyName, System.currentTimeMillis())
    }

    companion object {
        @Volatile
        private var INSTANCE: LaunchTimeDelegateSingle? = null

        fun getInstance(context: Context): LaunchTimeDelegateSingle {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LaunchTimeDelegateSingle(context).also { INSTANCE = it }
            }
        }
    }
}

