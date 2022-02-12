package com.example.wltest

import android.app.Application
import com.example.wltest.data.local.LocalStorageManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        LocalStorageManager.init(this)
    }
}