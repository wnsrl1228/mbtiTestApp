package com.mbtitestapp.data

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

enum class MbtiEnum {
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
