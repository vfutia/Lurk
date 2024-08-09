package com.vfutia.lurk.model

data class Comment (
    val likes: Int = 0,
    val body: String = "",
    val author: String = "",
    val created: Int = 0,
    val replies: List<Comment> = listOf()
)