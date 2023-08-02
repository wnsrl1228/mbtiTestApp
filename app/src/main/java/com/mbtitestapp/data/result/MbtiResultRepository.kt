package com.mbtitestapp.data.result

import kotlinx.coroutines.flow.Flow


class MbtiResultRepository(private val resultDao: MbtiResultDao) {

    fun getMbtiResultStream(id: Long): Flow<MbtiResult> = resultDao.getMbtiResult(id)
    fun getMbtiResultAndMbtiInfoStream(id: Long): Flow<MbtiResultAndMbtiInfo> = resultDao.getMbtiResultAndMbtiInfo(id)

    suspend fun addMbtiResult(result: MbtiResult) : Long = resultDao.insert(result)
}