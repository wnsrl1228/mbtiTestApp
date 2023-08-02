package com.mbtitestapp.data

import android.content.Context
import com.mbtitestapp.data.question.Option
import com.mbtitestapp.data.question.Question
import com.mbtitestapp.data.result.MbtiInfo
import org.json.JSONObject


enum class MbtiCategory {
    IE, SN, TF, PJ, ALL
}
enum class MbtiType {
    E, I, S, N, T, F, J, P
}

enum class Mbti {
    INFJ, INFP, INTJ, INTP,
    ISFJ, ISFP, ISTJ, ISTP,
    ENFJ, ENFP, ENTJ, ENTP,
    ESFJ, ESFP, ESTJ, ESTP, NONE
}


/**
 * db에 추가할 초기 데이터
 */
object InitialDataUtils {
    fun getInitialData(context: Context): List<MbtiInfo> {
        val json = context.assets.open("initial_data.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(json)
        val initialData = mutableListOf<MbtiInfo>()

        for (mbtiStr in jsonObject.keys()) {
            val name = jsonObject.getJSONObject(mbtiStr).getString("name")
            val shortDesc = jsonObject.getJSONObject(mbtiStr).getString("shortDesc")
            val detailedDesc = jsonObject.getJSONObject(mbtiStr).getString("detailedDesc")
            val mbti: Mbti = Mbti.values().find { it.name == mbtiStr }
                ?: throw IllegalArgumentException("Invalid MBTI type")
            initialData.add(MbtiInfo(mbti, name, shortDesc, detailedDesc))
        }

        return initialData
    }

    fun getInitialQuestionData(context: Context): List<Question> {
        val json = context.assets.open("initial_question_data.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(json)
        val initialQuestionData = mutableListOf<Question>()

        for (index in jsonObject.keys()) {
            val questionText = jsonObject.getJSONObject(index).getString("questionText")
            val mbtiCategoryStr = jsonObject.getJSONObject(index).getString("mbtiCategory")

            val mbtiCategory: MbtiCategory = MbtiCategory.values().find { it.name == mbtiCategoryStr }
                ?: throw IllegalArgumentException("Invalid MbtiCategory type")
            initialQuestionData.add(Question(index.toLong(), questionText, mbtiCategory))
        }

        return initialQuestionData
    }

    fun getInitialOptionData(context: Context): List<Option> {
        val json = context.assets.open("initial_option_data.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(json)
        val initialOptionData = mutableListOf<Option>()

        for (index in jsonObject.keys()) {
            val questionId = jsonObject.getJSONObject(index).getString("questionId").toLong()
            val optionText = jsonObject.getJSONObject(index).getString("optionText")
            val mbtiTypeStr = jsonObject.getJSONObject(index).getString("mbtiType")

            val mbtiType: MbtiType = MbtiType.values().find { it.name == mbtiTypeStr }
                ?: throw IllegalArgumentException("Invalid mbtiTypeStr type")
            initialOptionData.add(Option(index.toLong(), questionId, optionText, mbtiType))
        }

        return initialOptionData
    }
}