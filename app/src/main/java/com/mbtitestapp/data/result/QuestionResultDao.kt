package com.mbtitestapp.data.result


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionResultDao {

    @Insert
    suspend fun insertAll(questionResults: List<QuestionResult>)

    @Transaction
    @Query("SELECT * FROM question_result WHERE mbtiResultId = :id")
    fun getQuestionResultAndQuestion(id: Long): Flow<List<QuestionResultAndQuestion>>

    @Transaction
    @Query("SELECT * FROM question_result WHERE mbtiResultId = :mbtiResultId AND questionId = :questionId ")
    fun getQuestionResultAndQuestionAndOption(mbtiResultId: Long, questionId: Long): Flow<QuestionResultAndQuestion>

}