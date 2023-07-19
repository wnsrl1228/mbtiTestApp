package com.mbtitestapp.data

import android.content.Context
import com.mbtitestapp.data.result.MbtiInfo
import org.json.JSONObject

/**
 * 임시 데이터
 */

object DataSource {
    val questionDataList = listOf(
        QuestionData("나 너무 속상한 일이 있어서 미용실가서 파마했어",
            MbtiCategory.TF,
            MbtiOptionData("속상한데 왜 미용실을 가니", MbtiType.T),
            MbtiOptionData("무슨일 있어?" , MbtiType.F)),
        QuestionData("휴일에 밖에서 놀았더니 너무",
            MbtiCategory.IE,
            MbtiOptionData("충전된다", MbtiType.E),
            MbtiOptionData("집에가서 쉬어야지", MbtiType.I)),
        QuestionData("나는 모든 일을",
            MbtiCategory.PJ,
            MbtiOptionData("계획적으로", MbtiType.J),
            MbtiOptionData("즉흥적으로", MbtiType.P)),
        QuestionData("여행을 갔어",
            MbtiCategory.SN,
            MbtiOptionData("무조건 로컬음식", MbtiType.N),
            MbtiOptionData("걍 내가 좋아하는 음식", MbtiType.S)),
    )
}

enum class MbtiCategory {
    IE, SN, TF, PJ
}
enum class MbtiType {
    E, I, S, N, T, F, J, P
}

enum class Mbti {
    INFJ, INFP, INTJ, INTP,
    ISFJ, ISFP, ISTJ, ISTP,
    ENFJ, ENFP, ENTJ, ENTP,
    ESFJ, ESFP, ESTJ, ESTP,
}

data class QuestionData (
    val questionText: String,
    val type: MbtiCategory,
    val option1: MbtiOptionData,
    val option2: MbtiOptionData
)

data class MbtiOptionData (
    val optionText: String,
    val mbtiType: MbtiType
)

data class MbtiTestResultInfo(
    val scores: List<Int>,
    val mbti: Mbti
)

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
}