package com.vfutia.lurk.subreddit

import com.vfutia.lurk.model.Post
import com.vfutia.lurk.model.Subreddit

data class SubredditState (
    var subreddit: Subreddit? = null,
    var isLoadingFirstLoadPage: Boolean = true,
    var isLoadingNextPage: Boolean = false,
    var isRefreshing: Boolean = false,
    var hasLoadError: Boolean = false,
    var before: String = "",
    var after: String = "",
    var posts: List<Post> = listOf()
)