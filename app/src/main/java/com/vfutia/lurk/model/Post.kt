package com.vfutia.lurk.model

data class Post (
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
    val thumbnail: String?
)