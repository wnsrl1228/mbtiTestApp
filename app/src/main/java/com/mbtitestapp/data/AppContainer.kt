package com.mbtitestapp.data

import android.content.Context
import com.mbtitestapp.data.question.QuestionRepository
import com.mbtitestapp.data.result.MbtiInfoRepository
import com.mbtitestapp.data.result.MbtiResultRepository
import com.mbtitestapp.data.result.QuestionResultRepository

interface AppContainer {
    val mbtiInfoRepository: MbtiInfoRepository
    val questionRepository: QuestionRepository
    val mbtiResultRepository: MbtiResultRepository
    val questionResultRepository: QuestionResultRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val mbtiInfoRepository: MbtiInfoRepository by lazy {
        MbtiInfoRepository(MbtiDatabase.getDatabase(context).mbtiInfoDao())
    }
    override val questionRepository: QuestionRepository by lazy {
        QuestionRepository(MbtiDatabase.getDatabase(context).questionDao())
    }
    override val mbtiResultRepository: MbtiResultRepository by lazy {
        MbtiResultRepository(MbtiDatabase.getDatabase(context).mbtiResultDao())
    }
    override val questionResultRepository: QuestionResultRepository by lazy {
        QuestionResultRepository(MbtiDatabase.getDatabase(context).questionResultDao())
    }

}