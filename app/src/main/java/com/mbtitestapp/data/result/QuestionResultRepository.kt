package com.mbtitestapp.data.result

import kotlinx.coroutines.flow.Flow


class QuestionResultRepository(private val questionResultDao: QuestionResultDao) {

    suspend fun insertAllQuestionResult(questionResults: List<QuestionResult>) = questionResultDao.insertAll(questionResults)

    fun getQuestionResultAndMbtiResultStream(id: Long): Flow<List<QuestionResultAndMbtiResult>> =
        questionResultDao.getQuestionResultAndMbtiResult(id)

}