package com.mbtitestapp.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbtitestapp.data.result.MbtiResultAndMbtiInfo
import com.mbtitestapp.data.result.MbtiResultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PastResultUiState(
    val pastResultDataList: List<PastResultData> = emptyList()
)
class PastResultViewModel(
    private val mbtiResultRepository: MbtiResultRepository
) : ViewModel()  {

    private val _uiState = MutableStateFlow(PastResultUiState())
    val uiState: StateFlow<PastResultUiState> = _uiState.asStateFlow()

    init {
        initialize()
    }

    /**
     * 질문 목록 데이터 가져오기
     */
    fun initialize() {

        viewModelScope.launch {

            mbtiResultRepository.getMbtiResultAndMbtiInfoAllStream().collectLatest  { mbtiResultAndMbtiInfos ->
                val pastResultDataList = mbtiResultAndMbtiInfos.map { mbtiResultAndMbtiInfo ->
                    mbtiResultAndMbtiInfo.toPastResultData()
                }

                _uiState.value = _uiState.value.copy(
                    pastResultDataList = pastResultDataList,
                )
            }
        }
    }
}
data class PastResultData (
    val mbtiResultId: Long = 0,
    val mbti: String = "",
    var name: String = "",
    val testType: String = "",
    val createdDate: String = ""
)
fun MbtiResultAndMbtiInfo.toPastResultData() : PastResultData = PastResultData(
    mbtiResultId = mbtiResult.id,
    mbti = mbtiResult.mbti.name,
    name = mbtiInfo.name,
    testType = mbtiResult.testType.name,
    createdDate = formatDateToString(mbtiResult.createdAt)
)

fun formatDateToString(date: Date): String {
    val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return format.format(date)
}