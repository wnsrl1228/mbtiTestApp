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
    @Query("SELECT * FROM mbti_result WHERE id = :id")
    fun getQuestionResultAndMbtiResult(id: Long): Flow<List<QuestionResultAndMbtiResult>>
}