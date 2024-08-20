package com.vfutia.lurk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media (
    val redditVideo: RedditVideo?
) : Parcelable