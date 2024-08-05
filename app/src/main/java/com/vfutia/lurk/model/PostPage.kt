package com.vfutia.lurk.model

data class PostPage (
    val before: String = "",
    val after: String = "",
    val posts: List<Post> = listOf()
)