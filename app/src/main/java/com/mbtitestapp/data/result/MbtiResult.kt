package com.mbtitestapp.data.result

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mbtitestapp.data.Mbti
import com.mbtitestapp.data.MbtiCategory
import com.mbtitestapp.data.MbtiType
import java.util.Date


@Entity(tableName = "mbti_result")
data class MbtiResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mbti: Mbti,
    val testType : MbtiCategory,
    val scoreIE: Int,
    val scoreSN: Int,
    val scoreTF: Int,
    val scorePJ: Int,
    val createdAt: Date = Date()
)