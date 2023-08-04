package com.mbtitestapp.ui.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbtitestapp.data.MbtiCategory
import com.mbtitestapp.data.MbtiType
import com.mbtitestapp.data.result.QuestionResultAndQuestion
import com.mbtitestapp.data.result.QuestionResultRepository
import com.mbtitestapp.ui.select.RadioButtonOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class ResultsByQuestionUiState(
    val questionResultDataList: List<QuestionResultData> = emptyList()
)

class ResultsByQuestionViewModel(
    savedStateHandle: SavedStateHandle,
    private val questionResultRepository: QuestionResultRepository
) : ViewModel() {

    private val mbtiResultId: Long = checkNotNull(savedStateHandle[ResultsByQuestionDestination.mbtiResultIdArg])

    private val _uiState = MutableStateFlow(ResultsByQuestionUiState())
    val uiState: StateFlow<ResultsByQuestionUiState> = _uiState.asStateFlow()

    init {
        initialize()
    }

    /**
     * 질문 목록 데이터 가져오기
     */
    fun initialize() {

        viewModelScope.launch {

            questionResultRepository.getQuestionResultAndQuestionStream(mbtiResultId).collectLatest  { questionResultAndMbtiResults ->
                val questionResultDataList = questionResultAndMbtiResults.map { questionResultAndQuestion ->
                    questionResultAndQuestion.toQuestionResultData()
                }

                _uiState.value = _uiState.value.copy(
                    questionResultDataList = questionResultDataList,
                )
            }
        }
    }
    fun getMbtiResultId() : Long {
        return mbtiResultId
    }
}
data class QuestionResultData (
    val id: Long = 0,
    val questionText: String = "",
    val mbtiCategory: MbtiCategory = MbtiCategory.ALL,
    var selectedOption: RadioButtonOption = RadioButtonOption.NONE,
    val selectedMbtiType: MbtiType = MbtiType.X
)
fun QuestionResultAndQuestion.toQuestionResultData() : QuestionResultData = QuestionResultData(
    id = questionWithOptions.question.id,
    questionText = questionWithOptions.question.questionText,
    mbtiCategory = questionWithOptions.question.mbtiCategory,
    selectedOption = questionResult.selectedOption,
    selectedMbtiType = questionResult.selectedMbtiType
)