package com.mbtitestapp.data.question

import kotlinx.coroutines.flow.Flow

class QuestionRepository(private val questionDao: QuestionDao) {

    fun getQuestionWithOptionsStream() : Flow<List<QuestionWithOptions>> = questionDao.getQuestionWithOptions()

}
