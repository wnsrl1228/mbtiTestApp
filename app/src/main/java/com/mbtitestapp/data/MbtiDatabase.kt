package com.mbtitestapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mbtitestapp.data.result.MbtiInfoDao
import com.mbtitestapp.data.result.MbtiInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Database(entities = [MbtiInfo::class], version = 1, exportSchema = false)
abstract class MbtiDatabase : RoomDatabase() {

    abstract fun mbtiDao(): MbtiInfoDao

    companion object {
        @Volatile
        private var Instance: MbtiDatabase? = null

        fun getDatabase(context: Context): MbtiDatabase {

            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, MbtiDatabase::class.java, "mbti_database")
                    .build()

                // 초기 데이터 삽입
                CoroutineScope(Dispatchers.IO).launch {
                    if (instance.mbtiDao().getCount() == 0) {
                        val initialData = InitialDataUtils.getInitialData(context)
                        instance.mbtiDao().insertAll(initialData)
                    }
                }


                Instance = instance
                instance

            }
        }
    }
}