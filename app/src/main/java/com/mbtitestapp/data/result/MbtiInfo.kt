package com.mbtitestapp.data.result

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mbtitestapp.data.Mbti

@Entity(tableName = "mbti_info")
data class MbtiInfo(
    @PrimaryKey
    val mbti: Mbti,
    val description: String,
)
