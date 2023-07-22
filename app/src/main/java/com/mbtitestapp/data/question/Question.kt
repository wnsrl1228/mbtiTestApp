package com.mbtitestapp.data.question

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mbtitestapp.data.MbtiCategory

@Entity(tableName = "question")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val questionText: String,
    val mbtiCategory: MbtiCategory,
)

