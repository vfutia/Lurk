package com.vfutia.lurk.model

data class Page (
    val after: String? = "",
    val before: String? = "",
    val dist: Int,
    val children: List<PostWrapper> = listOf()
)