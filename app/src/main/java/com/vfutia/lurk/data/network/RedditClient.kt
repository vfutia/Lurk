package com.vfutia.lurk.data.network

import com.vfutia.lurk.model.ListingType
import com.vfutia.lurk.model.Page
import com.vfutia.lurk.model.PageResponse
import com.vfutia.lurk.model.Subreddit
import com.vfutia.lurk.model.SubredditWrapper
import com.vfutia.lurk.model.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RedditClient {
    @FormUrlEncoded
    @POST("api/v1/access_token")
    suspend fun fetchAccessToken(
        @Header("Authorization") authHeader: String = "Basic ${basicAuthHeader()}",
        @Field("device_id") deviceId: String,
        @Field("grant_type") grantType: String = "https://oauth.reddit.com/grants/installed_client"
    ) : TokenResponse

    @GET("r/{subreddit}/{listingType}")
    suspend fun fetchPosts(
        @Path("subreddit") subreddit: String,
        @Path("listingType") listingType: String = ListingType.Hot.value,
        @Query("after") after: String? = null
    ) : PageResponse

    @GET("{listingType}")
    suspend fun fetchFrontPage(
        @Path("listingType") listingType: String = ListingType.Hot.value,
        @Query("after") after: String? = null
    ) : PageResponse

    @GET("user/{username}")
    suspend fun fetchUser(
        @Path("username") username: String
    )

    @GET("r/{subreddit}/about")
    suspend fun fetchSubreddit(
        @Path("subreddit") subreddit: String
    ) : SubredditWrapper

    @GET("r/{subreddit}/comments/{postId}/_")
    suspend fun fetchComments(
        @Path("subreddit") subreddit: String,
        @Path("postId") postId: String
    ) : SubredditWrapper
}