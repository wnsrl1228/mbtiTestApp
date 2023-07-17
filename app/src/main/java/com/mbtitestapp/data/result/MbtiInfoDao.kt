package com.mbtitestapp.data.result

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mbtitestapp.data.Mbti
import kotlinx.coroutines.flow.Flow


@Dao
interface MbtiInfoDao {
    @Query("SELECT * from mbti_info WHERE mbti = :mbti") // 복잡하면 이렇게 씀
    fun getMbtiInfo(mbti: Mbti): Flow<MbtiInfo>

    @Insert
    fun insertAll(mbtiInfo: List<MbtiInfo>)

}