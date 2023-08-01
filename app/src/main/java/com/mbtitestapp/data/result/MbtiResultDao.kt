package com.mbtitestapp.data.result

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MbtiResultDao {
    @Query("SELECT * from mbti_result WHERE id = :id")
    fun getMbtiResult(id: Long): Flow<MbtiResult>

    @Insert
    fun insert(result: MbtiResult)
}