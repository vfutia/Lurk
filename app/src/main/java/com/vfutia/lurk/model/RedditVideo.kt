package com.vfutia.lurk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RedditVideo (
    val width: Int,
    val height: Int,
    val fallbackUrl: String,
    val dashUrl: String,
    val hlsUrl: String,
    val isGif: Boolean
) : Parcelable