package com.mbtitestapp.data.question

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface OptionDao {

    @Insert
    fun insertAll(options: List<Option>)
}