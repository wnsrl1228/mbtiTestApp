package com.mbtitestapp.data.result

import androidx.room.Embedded
import androidx.room.Relation

data class MbtiResultAndMbtiInfo(
    @Embedded val mbtiResult: MbtiResult,
    @Relation(
        parentColumn = "mbti",
        entityColumn = "mbti"
    )
    val mbtiInfo: MbtiInfo
)
