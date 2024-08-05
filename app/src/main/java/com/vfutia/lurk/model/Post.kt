package com.vfutia.lurk.model

class Post (
    val id: String,
    val title: String,
    val subreddit: String,
    val downs: Int,
    val ups: Int,
    val isMeta: Boolean,
    val score: Int,
    created: Long,
    val pinned: Boolean,
    val over18: Boolean,
    val author: String,
    val url: String,
    val isVideo: Boolean,
    val preview: Preview?,
    val numComments: Int,
    val thumbnail: String?
) {
    //This gets returned to us in seconds.  Convert to millis.
    val created: Long = created
        get() {
            return field * 1000
        }
}