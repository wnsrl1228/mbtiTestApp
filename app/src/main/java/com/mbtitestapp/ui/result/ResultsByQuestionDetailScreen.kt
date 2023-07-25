package com.mbtitestapp.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.mbtitestapp.MbitTopAppBar
import com.mbtitestapp.R
import com.mbtitestapp.data.MbtiCategory
import com.mbtitestapp.data.MbtiType
import com.mbtitestapp.navigation.NavigationDestination
import com.mbtitestapp.ui.menu.MbtiTestMenuDestination
import com.mbtitestapp.ui.select.MbtiOptionData
import com.mbtitestapp.ui.select.OptionRadioButton
import com.mbtitestapp.ui.select.OtherRadioButton
import com.mbtitestapp.ui.select.QuestionData
import com.mbtitestapp.ui.select.RadioButtonOption
import com.mbtitestapp.ui.select.SelectUiState
import com.mbtitestapp.ui.select.SelectViewModel
import com.mbtitestapp.ui.theme.MbtiTestAppTheme

object ResultsByQuestionDetailDestination : NavigationDestination {
    override val route = "results_by_question_detail"
    override val titleRes = R.string.app_name
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun ResultsByQuestionDetailScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: SelectViewModel,
    navBackStackEntry: NavBackStackEntry
) {
    // NavBackStackEntry에서 arguments를 가져옵니다.
    val arguments = navBackStackEntry.arguments

    // Int 형태의 itemId 인자를 추출합니다.
    val currentQuestionNum: Int = arguments?.getInt(ResultsByQuestionDetailDestination.itemIdArg) ?: -1

    Scaffold(
        topBar = {
            MbitTopAppBar(
                title = stringResource(MbtiTestMenuDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        ResultsByQuestionDetailBody(
            uiState = viewModel.uiState.collectAsState().value,
            currentQuestionNum = currentQuestionNum,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
        )
    }
}

@Composable
fun ResultsByQuestionDetailBody(
    uiState: SelectUiState,
    currentQuestionNum: Int,
    modifier: Modifier = Modifier,
) {

    val questionDataList = uiState.questionDataList                // mbti 테스트 관련 데이터

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .padding(horizontal = 32.dp),
    ) {

        Text(
            text = questionDataList[currentQuestionNum].questionText,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            modifier = Modifier.height(120.dp)
        )

        ResultQuestionOption(
            optionText1 = questionDataList[currentQuestionNum].option1.optionText,
            optionText2 = questionDataList[currentQuestionNum].option2.optionText,
            selectedOption = uiState.selectedOptions[currentQuestionNum]
        )
    }
}
@Composable
fun ResultQuestionOption(
    optionText1: String,
    optionText2: String,
    selectedOption: RadioButtonOption

) {
    OptionRadioButton(
        selected = selectedOption == RadioButtonOption.OPTION_1,
        text = optionText1
    )

    Text(
        text = "VS",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )

    OptionRadioButton(
        selected = selectedOption == RadioButtonOption.OPTION_2,
        text = optionText2
    )

    OtherRadioButton(
        selected = selectedOption == RadioButtonOption.OTHER,
    )
}

@Preview(showBackground = true)
@Composable
fun ResultsByQuestionDetailScreenPreview() {
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
        ResultsByQuestionDetailBody(SelectUiState(dummyQuestionDataList, dummySelectedOptions), 0)
    }
}