package com.vfutia.lurk.data.network

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(private val sharedPreferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}