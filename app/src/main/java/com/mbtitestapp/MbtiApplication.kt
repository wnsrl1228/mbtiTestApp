package com.mbtitestapp

import android.app.Application
import com.mbtitestapp.data.AppContainer
import com.mbtitestapp.data.AppDataContainer

class MbtiApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}