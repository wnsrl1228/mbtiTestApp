package com.mbtitestapp.data.result

import androidx.room.Embedded
import androidx.room.Relation
import com.mbtitestapp.data.question.Question


data class QuestionResultAndQuestion(
    @Embedded val questionResult: QuestionResult,
    @Relation(
        parentColumn = "questionId",
        entityColumn = "id"
    )
    val question: Question
)