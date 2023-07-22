package com.mbtitestapp.data

import android.content.Context
import com.mbtitestapp.data.question.QuestionRepository
import com.mbtitestapp.data.result.MbtiInfoRepository

interface AppContainer {
    val mbtiInfoRepository: MbtiInfoRepository
    val questionRepository: QuestionRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val mbtiInfoRepository: MbtiInfoRepository by lazy {
        MbtiInfoRepository(MbtiDatabase.getDatabase(context).mbtiDao())
    }
    override val questionRepository: QuestionRepository by lazy {
        QuestionRepository(MbtiDatabase.getDatabase(context).questionDao())
    }

}