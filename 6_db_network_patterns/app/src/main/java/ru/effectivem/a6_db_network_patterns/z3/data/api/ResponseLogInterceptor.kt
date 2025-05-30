package ru.effectivem.a6_db_network_patterns.z3.data.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ResponseLogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        Log.d(
            "api intercept",
            "Код ответа: ${response.code}${if (response.isSuccessful) ", Успешно" else ", Успешно"}"
        )

        return response
    }
}