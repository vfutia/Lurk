package com.vfutia.lurk.model

data class PostPage (
    val before: String? = null,
    val after: String? = null,
    val posts: List<Post> = listOf()
)