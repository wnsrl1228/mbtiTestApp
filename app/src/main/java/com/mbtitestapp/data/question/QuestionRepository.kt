package com.mbtitestapp.data.question

import com.mbtitestapp.data.MbtiCategory
import kotlinx.coroutines.flow.Flow

class QuestionRepository(private val questionDao: QuestionDao) {

    fun getQuestionWithOptionsAll() : Flow<List<QuestionWithOptions>> = questionDao.getQuestionAll()

    fun getQuestionWithOptionsByCategory(mbtiCategory: MbtiCategory) : Flow<List<QuestionWithOptions>> = questionDao.getQuestionByCategory(mbtiCategory)

}
