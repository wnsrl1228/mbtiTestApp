package com.mbtitestapp.data.result

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ResultDao {
    @Query("SELECT * from result WHERE id = :id")
    fun getResult(id: Long): Flow<Result>

    @Insert
    fun insert(result: Result)

    @Query("SELECT COUNT(*) FROM result")
    suspend fun getCount(): Int
}