package com.vfutia.lurk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageSource (
    val url: String,
    val width: Int,
    val height: Int
) : Parcelable