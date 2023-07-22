package com.mbtitestapp.data.question

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface QuestionDao {

    @Insert
    fun insertAll(questions: List<Question>)
}