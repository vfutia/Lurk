package com.vfutia.lurk.subreddit

import com.vfutia.lurk.model.Post

data class SubredditState (
    var currentSubreddit: String = "news",
    var isLoadingFirstLoadPage: Boolean = true,
    var isLoadingNextPage: Boolean = false,
    var hasLoadError: Boolean = false,
    var before: String = "",
    var after: String = "",
    var posts: List<Post> = listOf()
)