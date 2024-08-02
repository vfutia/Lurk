package com.vfutia.lurk.hilt

import android.content.SharedPreferences
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.vfutia.lurk.data.network.AuthenticationInterceptor
import com.vfutia.lurk.data.network.RedditClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton


@Module(includes = [
    AndroidModule::class
])
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideRedditClient(sharedPreferences: SharedPreferences): RedditClient {
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .addNetworkInterceptor(AuthenticationInterceptor(sharedPreferences))
            .build()

        val objectMapper = ObjectMapper()
            .registerKotlinModule()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)

        return Retrofit.Builder()
            .baseUrl("https://www.reddit.com/api/")
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .client(client)
            .build()
            .create(RedditClient::class.java)
    }
}