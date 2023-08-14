package com.mbtitestapp

import android.app.Application
import com.mbtitestapp.data.AppContainer
import com.mbtitestapp.data.AppDataContainer
import com.mbtitestapp.data.MbtiDatabase

class MbtiApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        MbtiDatabase.getDatabase(applicationContext)  // db 초기화
        container = AppDataContainer(this)
    }
}