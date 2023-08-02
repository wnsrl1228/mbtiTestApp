package com.mbtitestapp.ui.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbtitestapp.data.Mbti
import com.mbtitestapp.data.result.MbtiInfo
import com.mbtitestapp.data.result.MbtiResult
import com.mbtitestapp.data.result.MbtiResultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class MbtiResultUiState(
    val mbti: Mbti = Mbti.NONE,
    val mbtiInfoData: MbtiInfoData = MbtiInfoData(),
    val mbtiResultData: MbtiResultData = MbtiResultData(),
)

class MbtiResultViewModel(
    savedStateHandle: SavedStateHandle,
    private val mbtiResultRepository: MbtiResultRepository
) : ViewModel(){

    private val mbtiResultId: Long = checkNotNull(savedStateHandle[MbtiResultDestination.mbtiResultIdArg])

    private val _uiState = MutableStateFlow(MbtiResultUiState())
    val uiState: StateFlow<MbtiResultUiState> = _uiState.asStateFlow()

    init {
        initialize()
    }

    /**
     * 결과 데이터 가져오기
     */
    fun initialize() {

        viewModelScope.launch {

            mbtiResultRepository.getMbtiResultAndMbtiInfoStream(mbtiResultId).collectLatest { mbtiResultAndMbtiInfo ->

                _uiState.value = _uiState.value.copy(
                    mbti = mbtiResultAndMbtiInfo.mbtiResult.mbti,
                    mbtiResultData = mbtiResultAndMbtiInfo.mbtiResult.toMbtiResultData(),
                    mbtiInfoData = mbtiResultAndMbtiInfo.mbtiInfo.toMbtiInfoData()
                )
            }
        }
    }
}

data class MbtiResultData(
    val scoreIE: Int = 0,
    val scoreSN: Int = 0,
    val scoreTF: Int = 0,
    val scorePJ: Int = 0,
)
fun MbtiResult.toMbtiResultData(): MbtiResultData = MbtiResultData(
    scoreIE = scoreIE,
    scoreSN = scoreSN,
    scoreTF = scoreTF,
    scorePJ = scorePJ,
)
data class MbtiInfoData(
    val name: String = "",
    val shortDesc: String = "",
    val detailedDesc: String = "",
)
fun MbtiInfo.toMbtiInfoData(): MbtiInfoData = MbtiInfoData(
    name = name,
    shortDesc = shortDesc,
    detailedDesc = detailedDesc
)