package com.mbtitestapp.data.question

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Question 과 Option은 1대 다 관계
 */
data class QuestionWithOptions(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val options: List<Option>
)