package com.mbtitestapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mbtitestapp.data.question.Option
import com.mbtitestapp.data.question.OptionDao
import com.mbtitestapp.data.question.Question
import com.mbtitestapp.data.question.QuestionDao
import com.mbtitestapp.data.question.QuestionWithOptions
import com.mbtitestapp.data.result.MbtiInfoDao
import com.mbtitestapp.data.result.MbtiInfo
import com.mbtitestapp.data.result.ResultDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Database(
    entities = [MbtiInfo::class, Question::class, Option::class],
    version = 1,
    exportSchema = false
)
abstract class MbtiDatabase : RoomDatabase() {

    abstract fun mbtiDao(): MbtiInfoDao

    abstract fun questionDao(): QuestionDao
    abstract fun optionDao(): OptionDao
    abstract fun resultDao(): ResultDao

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
                    if (instance.mbtiDao().getCount() == 0) {
                        val initialData = InitialDataUtils.getInitialData(context)
                        instance.mbtiDao().insertAll(initialData)
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