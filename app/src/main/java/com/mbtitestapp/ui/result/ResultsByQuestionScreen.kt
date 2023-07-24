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
import com.mbtitestapp.MbitTopAppBar
import com.mbtitestapp.R
import com.mbtitestapp.data.MbtiCategory
import com.mbtitestapp.data.MbtiType
import com.mbtitestapp.navigation.NavigationDestination
import com.mbtitestapp.ui.menu.MbtiTestMenuDestination
import com.mbtitestapp.ui.select.MbtiOptionData
import com.mbtitestapp.ui.select.QuestionData
import com.mbtitestapp.ui.select.RadioButtonOption
import com.mbtitestapp.ui.select.SelectUiState
import com.mbtitestapp.ui.select.SelectViewModel
import com.mbtitestapp.ui.theme.MbtiTestAppTheme

object ResultsByQuestionDestination : NavigationDestination {
    override val route = "results_by_question"
    override val titleRes = R.string.app_name
}

@Composable
fun ResultsByQuestionScreen(
    viewModel: SelectViewModel,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun ResultsByQuestionBody(
    uiState: SelectUiState,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier // topbar 가리는 문제 해결
    ) {
        itemsIndexed(uiState.questionDataList) { index, questionData ->
            Card(
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable {

                    }
            
            ) {

                var selectedOption = ""
                when(uiState.selectedOptions[index]) {
                    RadioButtonOption.OPTION_1 -> selectedOption = questionData.option1.mbtiType.name
                    RadioButtonOption.OPTION_2 -> selectedOption = questionData.option2.mbtiType.name
                    else -> selectedOption = "―"
                }

                Row(
                    modifier = Modifier.padding(18.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "${1 + index}. ${questionData.questionText}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = "${questionData.mbtiCategory} / ${selectedOption}" )
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
        QuestionData("안녕1", MbtiCategory.PJ, MbtiOptionData("1번", MbtiType.E), MbtiOptionData("2번", MbtiType.E)),
        QuestionData("안녕2", MbtiCategory.PJ, MbtiOptionData("1번", MbtiType.E), MbtiOptionData("2번", MbtiType.I)),
        QuestionData("안녕2", MbtiCategory.PJ, MbtiOptionData("1번", MbtiType.F), MbtiOptionData("2번", MbtiType.T)),
        QuestionData("안녕2", MbtiCategory.PJ, MbtiOptionData("1번", MbtiType.T), MbtiOptionData("2번", MbtiType.F)),
        // 다른 항목들도 추가
    )
    val dummySelectedOptions = listOf(
    RadioButtonOption.OPTION_2,RadioButtonOption.OPTION_2,RadioButtonOption.OPTION_2,
        RadioButtonOption.OTHER
    )


    MbtiTestAppTheme {
        ResultsByQuestionBody(SelectUiState(dummyQuestionDataList, dummySelectedOptions))
    }
}