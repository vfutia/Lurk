package com.vfutia.lurk.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Post (
    val id: String,
    val title: String,
    val subreddit: String,
    val downs: Int,
    val ups: Int,
    val isMeta: Boolean,
    val score: Int,
    val created: Long,
    val pinned: Boolean,
    val over18: Boolean,
    val author: String,
    val url: String,
    val isVideo: Boolean,
    val preview: Preview?,
    val numComments: Int,
    val thumbnail: String?,
    val media: Media?,
    val isSelf: Boolean = false,
    val selftext: String? = null
) : Parcelable {
    //This gets returned to us in seconds.  Convert to millis.
    @IgnoredOnParcel
    val createdMillis: Long = created
        get() {
            return field * 1000
        }
}