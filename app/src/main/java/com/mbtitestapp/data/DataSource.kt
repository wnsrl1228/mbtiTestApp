package com.mbtitestapp.data

/**
 * 임시 데이터
 */

object DataSource {
    val questionDataList = listOf(
        QuestionData("나 너무 속상한 일이 있어서 미용실가서 파마했어",
            MbtiType.TF,
            Pair("속상한데 왜 미용실을 가니", "T"),
            Pair("무슨일 있어?" , "F")),
        QuestionData("휴일에 밖에서 놀았더니 너무",
            MbtiType.IE,
            Pair("충전된다", "E"),
            Pair("집에가서 쉬어야지", "I")),
        QuestionData("나는 모든 일을",
            MbtiType.PJ,
            Pair("계획적으로", "J"),
            Pair("즉흥적으로", "P")),
        QuestionData("여행을 갔어",
            MbtiType.SN,
            Pair("무조건 로컬음식", "N"),
            Pair("걍 내가 좋아하는 음식", "S")),
    )
}

enum class MbtiType {
    IE, SN, TF, PJ
}
data class QuestionData (
    val questionText: String,
    val type: MbtiType,
    val option1: Pair<String, String>,
    val option2: Pair<String, String>
)