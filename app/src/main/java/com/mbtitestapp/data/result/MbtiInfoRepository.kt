package com.mbtitestapp.data.result

import com.mbtitestapp.data.Mbti
import kotlinx.coroutines.flow.Flow

class MbtiInfoRepository(private val mbtiInfoDao: MbtiInfoDao) {

    fun getMbtiInfoStream(mbti: Mbti): Flow<MbtiInfo> = mbtiInfoDao.getMbtiInfo(mbti)
}