package com.vfutia.lurk.data.network

import com.vfutia.lurk.model.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface RedditClient {
    @FormUrlEncoded
    @POST("v1/access_token")
    suspend fun fetchAccessToken(
        @Header("Authorization") authHeader: String = "Basic ${basicAuthHeader()}",
        @Field("device_id") deviceId: String,
        @Field("grant_type") grantType: String = "https://oauth.reddit.com/grants/installed_client"
    ) : TokenResponse
}