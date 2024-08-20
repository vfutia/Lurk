package com.vfutia.lurk.model

import android.net.Uri
import android.util.Size
import androidx.room.Entity
import androidx.room.PrimaryKey

class Subreddit (
    val displayNamePrefixed: String,
    val communityIcon: String? = null,
    val showMediaPreview: Boolean = false,
    bannerBackgroundImage: String? = null,
    iconImage: String? = null,
    val subscribers: Int,
    val created: Long,
    bannerSize: Array<Int>?,
    iconSize: Array<Int>?
) {
    val bannerBackgroundImage: String? = bannerBackgroundImage
        get() {
            return Uri.parse(field).buildUpon().clearQuery().build().toString()
        }

    val iconImg: String? = iconImage
        get() {
            return Uri.parse(field).buildUpon().clearQuery().build().toString()
        }

    val bannerSize: Size = Size (
        bannerSize?.get(0) ?: 0,
        bannerSize?.get(1) ?: 0
    )

    val iconSize: Size = Size (
        iconSize?.get(0) ?: 0,
        iconSize?.get(1) ?: 0
    )
}