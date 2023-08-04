package com.mbtitestapp.ui.result

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mbtitestapp.MbitTopAppBar
import com.mbtitestapp.R
import com.mbtitestapp.data.MbtiCategory
import com.mbtitestapp.data.MbtiType
import com.mbtitestapp.navigation.NavigationDestination
import com.mbtitestapp.ui.AppViewModelProvider
import com.mbtitestapp.ui.menu.MbtiTestMenuDestination
import com.mbtitestapp.ui.select.OptionData
import com.mbtitestapp.ui.select.QuestionData
import com.mbtitestapp.ui.select.RadioButtonOption

object ResultsByQuestionDestination : NavigationDestination {
    override val route = "results_by_question"
    override val titleRes = R.string.app_name
    const val mbtiResultIdArg = "mbtiResultIdArg"
    val routeWithArgs = "${this.route}/{$mbtiResultIdArg}"
}

@Composable
fun ResultsByQuestionScreen(
    viewModel: ResultsByQuestionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    navigateToResultsByQuestionDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            MbitTopAppBar(
                title = stringResource(MbtiTestMenuDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        },
    ) { innerPadding ->
        ResultsByQuestionBody(
            uiState = viewModel.uiState.collectAsState().value,
            mbtiResultId = viewModel.getMbtiResultId(),
            onItemClick = navigateToResultsByQuestionDetail,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun ResultsByQuestionBody(
    uiState: ResultsByQuestionUiState,
    mbtiResultId: Long,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier // topbar 가리는 문제 해결
    ) {
        itemsIndexed(uiState.questionResultDataList) { index, questionResultData ->
            Card(
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable {
                        onItemClick(questionResultData.id)
                    }

            ) {

                Row(
                    modifier = Modifier.padding(18.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "${1 + index}. ${questionResultData.questionText}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = "${questionResultData.mbtiCategory} / ${questionResultData.selectedMbtiType}" )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ResultsByQuestionScreenPreview() {
    // 임의의 더미 데이터 생성
    val dummyQuestionDataList = listOf(
        QuestionData(1, "안녕1", MbtiCategory.PJ, OptionData("1번", MbtiType.E), OptionData("2번", MbtiType.E)),
        QuestionData(2, "안녕2", MbtiCategory.PJ, OptionData("1번", MbtiType.E), OptionData("2번", MbtiType.I)),
        QuestionData(3, "안녕2", MbtiCategory.PJ, OptionData("1번", MbtiType.F), OptionData("2번", MbtiType.T)),
        QuestionData(4, "안녕2", MbtiCategory.PJ, OptionData("1번", MbtiType.T), OptionData("2번", MbtiType.F)),
        // 다른 항목들도 추가
    )
    val dummySelectedOptions = listOf(
    RadioButtonOption.OPTION_2,RadioButtonOption.OPTION_2,RadioButtonOption.OPTION_2,
        RadioButtonOption.OTHER
    )


//    MbtiTestAppTheme {
//        ResultsByQuestionBody(SelectUiState(dummyQuestionDataList, dummySelectedOptions), onItemClick = {})
//    }
}