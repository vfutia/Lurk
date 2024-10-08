package com.vfutia.lurk.data.network

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.vfutia.lurk.BuildConfig
import com.vfutia.lurk.R
import com.vfutia.lurk.extension.getToken
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(
    @ApplicationContext private val context: Context,
    private val sharedPreferences: EncryptedSharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        //Apply the bearer token if we are making any call other than the token request
        if (!request.url.pathSegments.contains("access_token")) {
            builder.header("Authorization", "Bearer ${sharedPreferences.getToken()}")
        }

        return chain.proceed(builder
            .header("User-Agent", buildUserAgent()) //Add user agent!!
            .build())
    }

    private fun buildUserAgent(): String {
        return "${context.getString(R.string.app_name)} v${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"
    }
}