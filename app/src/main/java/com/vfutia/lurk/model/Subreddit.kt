package com.vfutia.lurk.model

import android.net.Uri
import java.net.URL

class Subreddit (
    val displayNamePrefixed: String,
    val communityIcon: String? = null,
    bannerBackgroundImage: String? = null,
) {
    val bannerBackgroundImage: String? = bannerBackgroundImage
        get() {
            return Uri.parse(field).buildUpon().clearQuery().build().toString()
        }
}