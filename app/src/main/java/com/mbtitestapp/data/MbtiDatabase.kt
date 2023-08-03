package com.mbtitestapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mbtitestapp.data.question.Option
import com.mbtitestapp.data.question.OptionDao
import com.mbtitestapp.data.question.Question
import com.mbtitestapp.data.question.QuestionDao
import com.mbtitestapp.data.result.MbtiInfoDao
import com.mbtitestapp.data.result.MbtiInfo
import com.mbtitestapp.data.result.MbtiResult
import com.mbtitestapp.data.result.MbtiResultDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [MbtiInfo::class, Question::class, Option::class, MbtiResult::class],
    version = 2,
    exportSchema = false
)
abstract class MbtiDatabase : RoomDatabase() {

    abstract fun mbtiInfoDao(): MbtiInfoDao

    abstract fun questionDao(): QuestionDao
    abstract fun optionDao(): OptionDao
    abstract fun mbtiResultDao(): MbtiResultDao

    companion object {
        @Volatile
        private var Instance: MbtiDatabase? = null

        fun getDatabase(context: Context): MbtiDatabase {

            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, MbtiDatabase::class.java, "mbti_database")
                    .fallbackToDestructiveMigration()
                    .build()

                // 초기 데이터 삽입
                CoroutineScope(Dispatchers.IO).launch {
                    if (instance.mbtiInfoDao().getCount() == 0) {
                        val initialData = InitialDataUtils.getInitialData(context)
                        instance.mbtiInfoDao().insertAll(initialData)
                    }

                    if (instance.questionDao().getCount() == 0) {
                        val initialQuestionData = InitialDataUtils.getInitialQuestionData(context)
                        instance.questionDao().insertAll(initialQuestionData)
                    }

                    if (instance.optionDao().getCount() == 0) {
                        val initialOptionData = InitialDataUtils.getInitialOptionData(context)
                        instance.optionDao().insertAll(initialOptionData)
                    }
                }


                Instance = instance
                instance

            }
        }
    }
}