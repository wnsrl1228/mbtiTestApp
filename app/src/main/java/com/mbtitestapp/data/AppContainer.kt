package com.mbtitestapp.data

import android.content.Context
import com.mbtitestapp.data.question.QuestionRepository
import com.mbtitestapp.data.result.MbtiInfoRepository
import com.mbtitestapp.data.result.ResultRepository

interface AppContainer {
    val mbtiInfoRepository: MbtiInfoRepository
    val questionRepository: QuestionRepository
    val resultRepository: ResultRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val mbtiInfoRepository: MbtiInfoRepository by lazy {
        MbtiInfoRepository(MbtiDatabase.getDatabase(context).mbtiDao())
    }
    override val questionRepository: QuestionRepository by lazy {
        QuestionRepository(MbtiDatabase.getDatabase(context).questionDao())
    }
    override val resultRepository: ResultRepository by lazy {
        ResultRepository(MbtiDatabase.getDatabase(context).resultDao())
    }

}