package com.mbtitestapp.data.question

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mbtitestapp.data.MbtiCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Insert
    fun insertAll(questions: List<Question>)

    @Query("SELECT COUNT(*) FROM question")
    suspend fun getCount(): Int


    @Transaction
    @Query("SELECT * FROM question")
    fun getQuestionAll(): Flow<List<QuestionWithOptions>>

    @Transaction
    @Query("SELECT * FROM question WHERE mbtiCategory = :mbtiCategory")
    fun getQuestionByCategory(mbtiCategory: MbtiCategory): Flow<List<QuestionWithOptions>>
}