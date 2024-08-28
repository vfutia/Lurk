package com.vfutia.lurk.data

import androidx.paging.PagingData
import com.vfutia.lurk.model.Favorite
import com.vfutia.lurk.model.ListingType
import com.vfutia.lurk.model.Page
import com.vfutia.lurk.model.PageResponse
import com.vfutia.lurk.model.PostPage
import com.vfutia.lurk.model.PostWrapper
import com.vfutia.lurk.model.Subreddit
import com.vfutia.lurk.model.TokenResponse
import kotlinx.coroutines.flow.Flow

interface RedditRepository {
    suspend fun fetchAccessToken(deviceId: String): TokenResponse
    suspend fun fetchUser(username: String)
    fun fetchPosts(subreddit: String? = null, listingType: ListingType = ListingType.Hot): Flow<PagingData<PostWrapper>>
    suspend fun fetchSubreddit(subreddit: String): Subreddit
    suspend fun addFavorite(subreddit: String): List<Favorite>
    suspend fun deleteFavorite(subreddit: String): List<Favorite>
    suspend fun getFavorites() : List<Favorite>
    suspend fun isFavorite(subreddit: String): Boolean
//    suspend fun fetchComments(subreddit: String, postId: String)
}