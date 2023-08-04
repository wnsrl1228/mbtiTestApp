package com.mbtitestapp.data.result

import androidx.room.Embedded
import androidx.room.Relation
import com.mbtitestapp.data.question.Option
import com.mbtitestapp.data.question.Question
import com.mbtitestapp.data.question.QuestionWithOptions


data class QuestionResultAndQuestion(
    @Embedded val questionResult: QuestionResult,
    @Relation(
        parentColumn = "questionId",
        entityColumn = "id",
        entity = Question::class,
    )
    val questionWithOptions: QuestionWithOptions,
)