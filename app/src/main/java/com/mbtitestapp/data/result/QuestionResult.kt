package com.mbtitestapp.data.result

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mbtitestapp.ui.select.RadioButtonOption

@Entity(tableName = "question_result")
data class QuestionResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mbtiResultId: Long,
    val questionId: Long,
    val selectedOption: RadioButtonOption
)
