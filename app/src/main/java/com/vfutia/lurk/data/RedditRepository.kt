package com.vfutia.lurk.data

import com.vfutia.lurk.model.ListingType
import com.vfutia.lurk.model.Page
import com.vfutia.lurk.model.PageResponse
import com.vfutia.lurk.model.PostPage
import com.vfutia.lurk.model.Subreddit
import com.vfutia.lurk.model.TokenResponse

interface RedditRepository {
    suspend fun fetchAccessToken(deviceId: String): TokenResponse
    suspend fun fetchUser(username: String)
    suspend fun fetchPosts(subreddit: String, listingType: ListingType = ListingType.Hot, after: String? = null): PostPage
    suspend fun fetchSubreddit(subreddit: String): Subreddit
    suspend fun fetchFrontPage(listingType: ListingType, after: String?): PostPage
}