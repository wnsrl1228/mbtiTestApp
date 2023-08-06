package com.mbtitestapp.data.result

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface MbtiResultDao {
    @Query("SELECT * from mbti_result WHERE id = :id")
    fun getMbtiResult(id: Long): Flow<MbtiResult>

    @Insert
    suspend fun insert(result: MbtiResult) : Long

    @Transaction
    @Query("SELECT * FROM mbti_result WHERE id = :id")
    fun getMbtiResultAndMbtiInfo(id: Long): Flow<MbtiResultAndMbtiInfo>

    @Transaction
    @Query("SELECT * FROM mbti_result ORDER BY mbti_result.createdAt DESC")
    fun getMbtiResultAndMbtiInfoAll(): Flow<List<MbtiResultAndMbtiInfo>>
}