package com.mbtitestapp.data.result

import kotlinx.coroutines.flow.Flow


class QuestionResultRepository(private val questionResultDao: QuestionResultDao) {

    suspend fun insertAllQuestionResult(questionResults: List<QuestionResult>) = questionResultDao.insertAll(questionResults)

    fun getQuestionResultAndQuestionStream(id: Long): Flow<List<QuestionResultAndQuestion>> =
        questionResultDao.getQuestionResultAndQuestion(id)


    fun getQuestionResultDetailStream(mbtiResultId: Long, questionId: Long): Flow<QuestionResultAndQuestion> =
        questionResultDao.getQuestionResultAndQuestionAndOption(mbtiResultId, questionId)
}