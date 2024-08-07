package com.vfutia.lurk.extension

import android.net.Uri
import java.util.Locale

fun Int.toUpvoteString(): String {
    return if (this > 1000) {
        "${String.format(Locale.getDefault(), "%.1f", this / 1000.0)}k"

    } else {
        this.toString()
    }
}

fun String?.isValidUrl(): Boolean {
    return try {
        if (this.isNullOrBlank()) {
            false
        } else {
            val scheme = Uri.parse(this).scheme
            return scheme?.contains("http") ?: false
        }
    } catch (e: Exception) {
        false
    }
}