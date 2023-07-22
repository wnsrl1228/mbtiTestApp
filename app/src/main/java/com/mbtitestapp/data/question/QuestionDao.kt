package com.mbtitestapp.data.question

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Insert
    fun insertAll(questions: List<Question>)

    @Query("SELECT COUNT(*) FROM question")
    suspend fun getCount(): Int

    //
    @Transaction
    @Query("SELECT * FROM question")
    fun getQuestionWithOptions(): Flow<List<QuestionWithOptions>>
}