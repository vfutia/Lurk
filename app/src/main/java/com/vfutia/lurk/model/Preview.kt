package com.vfutia.lurk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Preview (
    val images: List<Image> = listOf()
) : Parcelable