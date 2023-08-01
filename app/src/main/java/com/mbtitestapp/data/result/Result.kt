package com.mbtitestapp.data.result

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mbtitestapp.data.Mbti


@Entity(tableName = "result")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mbti: Mbti,
    val scoreIE: String,
    val scoreSN: String,
    val scoreTF: String,
    val scorePJ: String,
)