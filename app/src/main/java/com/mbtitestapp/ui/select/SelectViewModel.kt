package com.mbtitestapp.ui.select

import androidx.lifecycle.ViewModel
import com.mbtitestapp.data.MbtiCategory
import com.mbtitestapp.data.MbtiEnum
import com.mbtitestapp.data.MbtiOptionData
import com.mbtitestapp.data.MbtiType
import com.mbtitestapp.data.QuestionData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SelectUiState(
    val questionDataList: List<QuestionData> = emptyList(),
    val selectedOptions: List<RadioButtonOption> = List(4) {RadioButtonOption.NONE},
)

class SelectViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(SelectUiState(questionDataList = questionDataList()))
    val uiState: StateFlow<SelectUiState> = _uiState.asStateFlow()


    /**
     * 질문지에서 현재 선택한 항목으로 데이터 변경
     */
    fun setCurrentSelectedOption(index: Int, option: RadioButtonOption) {
        _uiState.update {currentState ->
            val updatedOptions = currentState.selectedOptions.toMutableList()
            updatedOptions[index] = option
            currentState.copy(selectedOptions = updatedOptions)
        }
    }

    /**
     * 테스트 결과 출력
     */
    fun getMbtiTestResult(): MbtiEnum {
        val selectedOptions = _uiState.value.selectedOptions
        val questionDataList = _uiState.value.questionDataList

        val mbtiScore: MutableMap<MbtiType, Int> = mutableMapOf(
            MbtiType.I to 0, MbtiType.E to 0, MbtiType.S to 0, MbtiType.N to 0,
            MbtiType.T to 0, MbtiType.F to 0, MbtiType.P to 0, MbtiType.J to 0,
        )
        /**
         * mbti 질문지에 대한 정보와 해당 질문지에서 선택된 옵션을 토대로 결과를 구한다.
         *
         * - 각 질문지의 Mbtitype을 확인한다.
         * - 그 다음 유저가 선택한 옵션을 통해 Mbti 타입별 점수를 추가시킨다.
         * - Mbti 타입별 점수를 토대로 결과를 도출한다.
         */
        for (i:Int in questionDataList.indices) {

            // 점수 업데이트
            when(questionDataList[i].type) {
                MbtiCategory.IE -> updateMBTIScore(selectedOptions[i], questionDataList[i], mbtiScore)
                MbtiCategory.TF -> updateMBTIScore(selectedOptions[i], questionDataList[i], mbtiScore)
                MbtiCategory.SN -> updateMBTIScore(selectedOptions[i], questionDataList[i], mbtiScore)
                MbtiCategory.PJ -> updateMBTIScore(selectedOptions[i], questionDataList[i], mbtiScore)
                else -> throw IllegalArgumentException("존재하지 않는 mbti 타입이다.")
            }
        }

        // 점수를 통해 mbti 결과 도출
        var resultMbtiText: String = ""
        resultMbtiText += if (mbtiScore[MbtiType.I] ?: 0 > mbtiScore[MbtiType.E] ?: 0) "I" else "E"
        resultMbtiText += if (mbtiScore[MbtiType.S] ?: 0 > mbtiScore[MbtiType.N] ?: 0) "S" else "N"
        resultMbtiText += if (mbtiScore[MbtiType.T] ?: 0 > mbtiScore[MbtiType.F] ?: 0) "T" else "F"
        resultMbtiText += if (mbtiScore[MbtiType.P] ?: 0 > mbtiScore[MbtiType.J] ?: 0) "P" else "J"

        // text -> enum 변경
        val resultMbti: MbtiEnum = MbtiEnum.values().find { it.name == resultMbtiText }
            ?: throw IllegalArgumentException("Invalid MBTI type")

        return resultMbti
    }

    private fun updateMBTIScore(
        selectedOption: RadioButtonOption,
        questionData: QuestionData,
        mbtiScore: MutableMap<MbtiType, Int>
    ) {
        if (selectedOption == RadioButtonOption.OPTION_1) {

            val mbtiType: MbtiType = questionData.option1.mbtiType
            mbtiScore[mbtiType] = mbtiScore[mbtiType]!!.plus(1)

        } else if (selectedOption == RadioButtonOption.OPTION_2) {

            val mbtiType: MbtiType = questionData.option2.mbtiType
            mbtiScore[mbtiType] = mbtiScore[mbtiType]!!.plus(1)
        }
    }

    /**
     * TODO : 초기 데이터 생성, 추후 변경
     */
    private fun questionDataList(): List<QuestionData> {
        return listOf(
            QuestionData("나 너무 속상한 일이 있어서 미용실가서 파마했어",
                MbtiCategory.TF,
                MbtiOptionData("속상한데 왜 미용실을 가니", MbtiType.T),
                MbtiOptionData("무슨일 있어?" , MbtiType.F)
            ),
            QuestionData("휴일에 밖에서 놀았더니 너무",
                MbtiCategory.IE,
                MbtiOptionData("충전된다", MbtiType.E),
                MbtiOptionData("집에가서 쉬어야지", MbtiType.I)
            ),
            QuestionData("나는 모든 일을",
                MbtiCategory.PJ,
                MbtiOptionData("계획적으로", MbtiType.J),
                MbtiOptionData("즉흥적으로", MbtiType.P)
            ),
            QuestionData("여행을 갔어",
                MbtiCategory.SN,
                MbtiOptionData("무조건 로컬음식", MbtiType.N),
                MbtiOptionData("걍 내가 좋아하는 음식", MbtiType.S)
            ),
        )
    }
}