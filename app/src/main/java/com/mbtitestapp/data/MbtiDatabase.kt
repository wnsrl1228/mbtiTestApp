package com.mbtitestapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mbtitestapp.data.result.MbtiInfoDao
import com.mbtitestapp.data.result.MbtiInfo

@Database(entities = [MbtiInfo::class], version = 1, exportSchema = false)
abstract class MbtiDatabase : RoomDatabase() {

    abstract fun mbtiDao(): MbtiInfoDao

    companion object {
        @Volatile
        private var Instance: MbtiDatabase? = null

        fun getDatabase(context: Context): MbtiDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MbtiDatabase::class.java, "mbti_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}