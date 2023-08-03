package com.mbtitestapp.data.result

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionResultAndMbtiResult(
    @Embedded val mbtiResult: MbtiResult,
    @Relation(
        parentColumn = "id",
        entityColumn = "mbtiResultId"
    )
    val questionResult: List<QuestionResult>
)