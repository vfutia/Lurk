package com.vfutia.lurk

import android.app.Application
import com.vfutia.lurk.data.network.RedditClient
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class LurkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}