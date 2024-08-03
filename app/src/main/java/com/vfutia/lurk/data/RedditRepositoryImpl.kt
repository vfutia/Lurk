package com.vfutia.lurk.data

import com.vfutia.lurk.data.network.RedditClient
import com.vfutia.lurk.model.ListingType
import com.vfutia.lurk.model.Page
import com.vfutia.lurk.model.PageResponse
import com.vfutia.lurk.model.Post
import com.vfutia.lurk.model.PostPage
import com.vfutia.lurk.model.TokenResponse
import javax.inject.Inject

class RedditRepositoryImpl @Inject constructor(
    private val redditClient: RedditClient
) : RedditRepository {

    override suspend fun fetchAccessToken(deviceId: String): TokenResponse {
        return redditClient.fetchAccessToken(deviceId = deviceId)
    }

    override suspend fun fetchUser(username: String) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPosts(subreddit: String, listingType: ListingType): PostPage {
        val content = redditClient.fetchPosts(subreddit, listingType.value)

        return PostPage (
            after = content.data.after,
            before = content.data.before,
            posts = content.data.children.map { wrapper -> wrapper.data }
        )
    }
}