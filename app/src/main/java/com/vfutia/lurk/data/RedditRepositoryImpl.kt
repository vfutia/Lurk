package com.vfutia.lurk.data

import com.vfutia.lurk.data.db.dao.FavoriteDao
import com.vfutia.lurk.data.network.RedditClient
import com.vfutia.lurk.model.Favorite
import com.vfutia.lurk.model.ListingType
import com.vfutia.lurk.model.Page
import com.vfutia.lurk.model.PageResponse
import com.vfutia.lurk.model.Post
import com.vfutia.lurk.model.PostPage
import com.vfutia.lurk.model.Subreddit
import com.vfutia.lurk.model.TokenResponse
import javax.inject.Inject

internal class RedditRepositoryImpl @Inject constructor(
    private val redditClient: RedditClient,
    private val favoriteDao: FavoriteDao
) : RedditRepository {

    override suspend fun fetchAccessToken(deviceId: String): TokenResponse {
        return redditClient.fetchAccessToken(deviceId = deviceId)
    }

    override suspend fun fetchUser(username: String) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchFrontPage(listingType: ListingType, after: String?): PostPage {
        val content = redditClient.fetchFrontPage(listingType.value, after)

        return PostPage (
            after = content.data.after ?: "",
            before = content.data.before ?: "",
            posts = content.data.children.map { wrapper -> wrapper.data }
        )
    }

    override suspend fun addFavorite(subreddit: String): List<Favorite> {
        favoriteDao.insert(Favorite(subreddit))
        return favoriteDao.getAll()
    }

    override suspend fun deleteFavorite(subreddit: String): List<Favorite> {
        favoriteDao.delete(Favorite(subreddit))
        return favoriteDao.getAll()
    }

    override suspend fun getFavorites(): List<Favorite> = favoriteDao.getAll()

    override suspend fun isFavorite(subreddit: String): Boolean = favoriteDao.isFavorite(subreddit) != null

//    override suspend fun fetchComments(subreddit: String, postId: String) {
//        return redditClient.fetchComments(subreddit, postId)
//    }

    override suspend fun fetchPosts(subreddit: String, listingType: ListingType, after: String?): PostPage {
        val content = redditClient.fetchPosts(subreddit, listingType.value, after)

        return PostPage (
            after = content.data.after ?: "",
            before = content.data.before ?: "",
            posts = content.data.children.map { wrapper -> wrapper.data }
        )
    }

    override suspend fun fetchSubreddit(subreddit: String): Subreddit {
        return redditClient.fetchSubreddit(subreddit).data
    }
}