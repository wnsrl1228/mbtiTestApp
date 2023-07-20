package com.mbtitestapp.data.question

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mbtitestapp.data.MbtiType

@Entity(tableName = "option")
data class Option(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val questionId: Long,
    val optionText: String,
    val mbtiType: MbtiType
)