package com.mbtitestapp.ui.select

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbtitestapp.data.Mbti
import com.mbtitestapp.data.MbtiCategory
import com.mbtitestapp.data.MbtiTestResultInfo
import com.mbtitestapp.data.MbtiType
import com.mbtitestapp.data.question.Option
import com.mbtitestapp.data.question.Question
import com.mbtitestapp.data.question.QuestionRepository
import com.mbtitestapp.data.result.MbtiInfoRepository
import com.mbtitestapp.data.result.MbtiInfo
import com.mbtitestapp.data.result.MbtiResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SelectUiState(
    val questionDataList: List<QuestionData> = emptyList(),
    val selectedOptions: List<RadioButtonOption> = List(36) {RadioButtonOption.NONE},
)

class SelectViewModel (
    savedStateHandle: SavedStateHandle,
    private val mbtiInfoRepository: MbtiInfoRepository,
    private val questionRepository: QuestionRepository,
    private val mbtiResultRepository: MbtiResultRepository
) : ViewModel()  {

    // 테스트 종류
    private val testType: String = checkNotNull(savedStateHandle[SelectDestination.mbtiCategoryArg])

    private val _uiState = MutableStateFlow(SelectUiState())
    val uiState: StateFlow<SelectUiState> = _uiState.asStateFlow()

    init {
        initialize()
    }

    /**
     * 테스트 종류에 맞는 질문 데이터 가져오기
     */
    fun initialize() {

        viewModelScope.launch {

            val questionStream = if (testType == MbtiCategory.ALL.name) {
                questionRepository.getQuestionWithOptionsAll()
            } else {
                questionRepository.getQuestionWithOptionsByCategory(MbtiCategory.valueOf(testType))
            }

            questionStream.collectLatest  { questionWithOptions ->
                val questionDataList = questionWithOptions.map { questionWithOption ->
                    val option1: MbtiOptionData = questionWithOption.options[0].toMbtiOptionData()
                    val option2: MbtiOptionData = questionWithOption.options[1].toMbtiOptionData()
                    questionWithOption.question.toQuestionData(option1, option2)
                }

                _uiState.value = _uiState.value.copy(
                    questionDataList = questionDataList,
                    selectedOptions = List(questionDataList.size) {RadioButtonOption.NONE}
                )
            }

        }
    }

    /**
     *  mbti 에 대한 결과정보
     */
    suspend fun getMbtiInfo(mbti: Mbti): Flow<MbtiInfo> {
        return mbtiInfoRepository.getMbtiInfoStream(mbti)
    }

    /**
     * 질문지에서 현재 선택한 항목으로 데이터 변경
     */
    val setCurrentSelectedOption: (Int, RadioButtonOption) -> Unit = { index, option ->
        _uiState.update { currentState ->
            val updatedOptions = currentState.selectedOptions.toMutableList()
            updatedOptions[index] = option
            currentState.copy(selectedOptions = updatedOptions)
        }
    }

    /**
     * 초기화
     */
    fun resetSelectUiState() {
        _uiState.value = SelectUiState()
    }

    /**
     * 테스트 결과 출력
     */
    fun getMbtiTestResultInfo(): MbtiTestResultInfo {
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
            when(questionDataList[i].mbtiCategory) {
                MbtiCategory.IE -> updateMBTIScore(selectedOptions[i], questionDataList[i], mbtiScore)
                MbtiCategory.TF -> updateMBTIScore(selectedOptions[i], questionDataList[i], mbtiScore)
                MbtiCategory.SN -> updateMBTIScore(selectedOptions[i], questionDataList[i], mbtiScore)
                MbtiCategory.PJ -> updateMBTIScore(selectedOptions[i], questionDataList[i], mbtiScore)
                else -> throw IllegalArgumentException("존재하지 않는 mbti 타입이다.")
            }
        }

        /**
         * 점수를 통해 mbti 결과 도출
         * - 점수가 0이면 ESFP
         */
        val mbtiTypes = listOf(MbtiType.I, MbtiType.E, MbtiType.N, MbtiType.S, MbtiType.T, MbtiType.F, MbtiType.J, MbtiType.P)
        val scores = mutableListOf<Int>()
        val resultMbtiText = StringBuilder()

        for (i in mbtiTypes.indices step 2) {
            val firstType = mbtiTypes[i]
            val secondType = mbtiTypes[i + 1]

            val firstScore = mbtiScore[firstType] ?: 0
            val secondScore = mbtiScore[secondType] ?: 0

            resultMbtiText.append(
                if (firstScore > secondScore) {
                    scores.add(firstScore)
                    firstType.name
                } else {
                    scores.add(secondScore)
                    secondType.name
                }
            )
        }

        // text -> enum 변경
        val resultMbti: Mbti = Mbti.values().find { it.name == resultMbtiText.toString() }
            ?: throw IllegalArgumentException("Invalid MBTI type")


        return MbtiTestResultInfo(scores, resultMbti)
    }

    private fun updateMBTIScore(
        selectedOption: RadioButtonOption,
        questionData: QuestionData,
        mbtiScore: MutableMap<MbtiType, Int>
    ) {
        if (selectedOption == RadioButtonOption.OPTION_1) {

            val mbtiType: MbtiType = questionData.option1.mbtiType
            mbtiScore[mbtiType] = mbtiScore[mbtiType]!!.plus(3)

        } else if (selectedOption == RadioButtonOption.OPTION_2) {

            val mbtiType: MbtiType = questionData.option2.mbtiType
            mbtiScore[mbtiType] = mbtiScore[mbtiType]!!.plus(3)
        }
    }

}
data class QuestionData (
    val questionText: String,
    val mbtiCategory: MbtiCategory,
    val option1: MbtiOptionData,
    val option2: MbtiOptionData
)
data class MbtiOptionData (
    val optionText: String,
    val mbtiType: MbtiType
)
fun Option.toMbtiOptionData(): MbtiOptionData = MbtiOptionData(
    optionText = optionText,
    mbtiType = mbtiType
)
fun Question.toQuestionData(option1: MbtiOptionData, option2: MbtiOptionData): QuestionData = QuestionData(
    questionText = questionText,
    mbtiCategory = mbtiCategory,
    option1 = option1,
    option2 = option2,
)