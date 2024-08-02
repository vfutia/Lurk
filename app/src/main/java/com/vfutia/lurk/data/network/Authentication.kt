package com.vfutia.lurk.data.network

import android.util.Base64
import com.vfutia.lurk.BuildConfig

fun basicAuthHeader(): String {
    return Base64.encodeToString("${BuildConfig.REDDIT_APP_ID}:".toByteArray(), Base64.NO_WRAP)
}