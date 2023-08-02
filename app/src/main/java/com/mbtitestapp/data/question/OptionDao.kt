package com.mbtitestapp.data.question

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OptionDao {

    @Insert
    suspend fun insertAll(options: List<Option>)

    @Query("SELECT COUNT(*) FROM option")
    suspend fun getCount(): Int
}