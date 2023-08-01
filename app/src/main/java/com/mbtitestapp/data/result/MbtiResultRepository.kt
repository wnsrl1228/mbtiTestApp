package com.mbtitestapp.data.result

import kotlinx.coroutines.flow.Flow


class MbtiResultRepository(private val resultDao: MbtiResultDao) {

    fun getMbtiResultStream(id: Long): Flow<MbtiResult> = resultDao.getMbtiResult(id)

    fun addMbtiResult(result: MbtiResult) = resultDao.insert(result)
}