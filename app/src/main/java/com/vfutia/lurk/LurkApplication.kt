package com.vfutia.lurk

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LurkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}