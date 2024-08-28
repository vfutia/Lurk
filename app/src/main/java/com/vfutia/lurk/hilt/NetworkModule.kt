package com.vfutia.lurk.hilt

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.vfutia.lurk.data.RedditRepository
import com.vfutia.lurk.data.RedditRepositoryImpl
import com.vfutia.lurk.data.network.AuthenticationInterceptor
import com.vfutia.lurk.data.network.RedditClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
abstract class NetworkModule {
    companion object {
        @Provides
        fun provideAuthenticationInterceptor(
            @ApplicationContext context: Context,
            sharedPreferences: EncryptedSharedPreferences
        ): AuthenticationInterceptor = AuthenticationInterceptor(context, sharedPreferences)

        @Provides
        @Singleton
        internal fun provideRedditClient(
            authenticationInterceptor: AuthenticationInterceptor
        ): RedditClient {
            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                })
                .addNetworkInterceptor(authenticationInterceptor)
                .build()

            val objectMapper = ObjectMapper()
                .registerKotlinModule()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)

            return Retrofit.Builder()
                .baseUrl("https://oauth.reddit.com/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build()
                .create(RedditClient::class.java)
        }
    }

    @Binds
    internal abstract fun provideRedditRepository(redditRepositoryImpl: RedditRepositoryImpl): RedditRepository
}