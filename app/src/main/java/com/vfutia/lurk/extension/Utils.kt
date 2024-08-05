package com.vfutia.lurk.extension

import java.util.Locale

fun Int.toUpvoteString(): String {
    return if (this > 1000) {
        "${String.format(Locale.getDefault(), "%.1f", this / 1000.0)}k"

    } else {
        this.toString()
    }
}