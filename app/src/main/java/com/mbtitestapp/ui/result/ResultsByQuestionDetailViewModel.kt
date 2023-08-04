package com.mbtitestapp.ui.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbtitestapp.data.result.QuestionResultAndQuestion
import com.mbtitestapp.data.result.QuestionResultRepository
import com.mbtitestapp.ui.select.OptionData
import com.mbtitestapp.ui.select.RadioButtonOption
import com.mbtitestapp.ui.select.toMbtiOptionData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


data class ResultsByQuestionDetailUiState(
    val questionResultDetail: QuestionResultDetail = QuestionResultDetail()
)

class ResultsByQuestionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val questionResultRepository: QuestionResultRepository
) : ViewModel() {

    private val mbtiResultIdArg : Long = checkNotNull(savedStateHandle[ResultsByQuestionDetailDestination.mbtiResultIdArg])
    private val questionIdArg: Long = checkNotNull(savedStateHandle[ResultsByQuestionDetailDestination.questionIdArg])

    private val _uiState = MutableStateFlow(ResultsByQuestionDetailUiState())
    val uiState: StateFlow<ResultsByQuestionDetailUiState> = _uiState.asStateFlow()

    init {
        initialize()
    }


    /**
     * 결과 id에 있는 질문 id 데이터와 옵션과 선택 결과
     */
    fun initialize() {

        viewModelScope.launch {
            questionResultRepository.getQuestionResultDetailStream(mbtiResultIdArg, questionIdArg).collectLatest  { questionResultDetail ->

                val questionResultDetail: QuestionResultDetail = questionResultDetail.toQuestionResultDetail()

                _uiState.value = _uiState.value.copy(
                    questionResultDetail = questionResultDetail,
                )
            }
        }
    }
}

data class QuestionResultDetail(
    val questionText: String = "",
    val option1: OptionData = OptionData(),
    val option2: OptionData = OptionData(),
    var selectedOption: RadioButtonOption = RadioButtonOption.NONE,
)
fun QuestionResultAndQuestion.toQuestionResultDetail() : QuestionResultDetail = QuestionResultDetail(
    questionText = questionWithOptions.question.questionText,
    option1 = questionWithOptions.options?.getOrNull(0)?.toMbtiOptionData() ?: OptionData(),
    option2 = questionWithOptions.options?.getOrNull(1)?.toMbtiOptionData() ?: OptionData(),
    selectedOption = questionResult.selectedOption,
)

