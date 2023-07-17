package com.mbtitestapp.data

import android.content.Context
import com.mbtitestapp.data.result.MbtiInfoRepository

interface AppContainer {
    val mbtiInfoRepository: MbtiInfoRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val mbtiInfoRepository: MbtiInfoRepository by lazy {
        MbtiInfoRepository(MbtiDatabase.getDatabase(context).mbtiDao())
    }
}