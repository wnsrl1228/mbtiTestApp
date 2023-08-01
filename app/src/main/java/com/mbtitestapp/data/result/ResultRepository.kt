package com.mbtitestapp.data.result

import com.mbtitestapp.data.Mbti
import kotlinx.coroutines.flow.Flow


class ResultRepository(private val resultDao: ResultDao) {

    fun getResultStream(id: Long): Flow<Result> = resultDao.getResult(id)

    fun addResult(result: Result) = resultDao.insert(result)
}