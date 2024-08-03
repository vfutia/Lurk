package com.vfutia.lurk.data.network

import android.content.Context
import android.content.SharedPreferences
import com.vfutia.lurk.BuildConfig
import com.vfutia.lurk.R
import com.vfutia.lurk.extension.getToken
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(
    @ApplicationContext private val context: Context,
    private val sharedPreferences: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed( chain.request()
            .newBuilder()
            .header("User-Agent", buildUserAgent()) //Add user agent!!
            .build())
    }

    private fun buildUserAgent(): String {
        return "${context.getString(R.string.app_name)} v${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"
    }
}