package com.mbtitestapp.data.result

import kotlinx.coroutines.flow.Flow


class MbtiResultRepository(private val mbtiResultDao: MbtiResultDao) {

    fun getMbtiResultStream(id: Long): Flow<MbtiResult> = mbtiResultDao.getMbtiResult(id)
    fun getMbtiResultAndMbtiInfoStream(id: Long): Flow<MbtiResultAndMbtiInfo> = mbtiResultDao.getMbtiResultAndMbtiInfo(id)

    fun getMbtiResultAndMbtiInfoAllStream() : Flow<List<MbtiResultAndMbtiInfo>> = mbtiResultDao.getMbtiResultAndMbtiInfoAll()
    suspend fun addMbtiResult(result: MbtiResult) : Long = mbtiResultDao.insert(result)
}