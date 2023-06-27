package com.mbtitestapp.ui.select

import androidx.lifecycle.ViewModel
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
     * TODO : 초기 데이터 생성, 추후 변경
     */
    private fun questionDataList(): List<QuestionData> {
        return listOf(
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
}