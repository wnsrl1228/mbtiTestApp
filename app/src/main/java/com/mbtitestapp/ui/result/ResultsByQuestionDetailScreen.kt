package com.mbtitestapp.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mbtitestapp.MbitTopAppBar
import com.mbtitestapp.R
import com.mbtitestapp.data.MbtiType
import com.mbtitestapp.navigation.NavigationDestination
import com.mbtitestapp.ui.AppViewModelProvider
import com.mbtitestapp.ui.menu.MbtiTestMenuDestination
import com.mbtitestapp.ui.select.OptionData
import com.mbtitestapp.ui.select.OptionRadioButton
import com.mbtitestapp.ui.select.OtherRadioButton
import com.mbtitestapp.ui.select.RadioButtonOption
import com.mbtitestapp.ui.theme.MbtiTestAppTheme

object ResultsByQuestionDetailDestination : NavigationDestination {
    override val route = "results_by_question_detail"
    override val titleRes = R.string.app_name
    const val mbtiResultIdArg = "mbtiResultIdArg"
    const val questionIdArg = "questionId"
    val routeWithArgs = "$route/{$mbtiResultIdArg}/{$questionIdArg}"
}

@Composable
fun ResultsByQuestionDetailScreen(
    navigateBack: () -> Unit,
    viewModel: ResultsByQuestionDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier,
) {

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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
        )
    }
}

@Composable
fun ResultsByQuestionDetailBody(
    uiState: ResultsByQuestionDetailUiState,
    modifier: Modifier = Modifier,
) {
    val questionResultDetail = uiState.questionResultDetail                // mbti 테스트 관련 데이터

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .padding(horizontal = 32.dp),
    ) {

        Text(
            text = questionResultDetail.questionText,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            modifier = Modifier.height(105.dp)
        )

        ResultQuestionOption(
            option1 = questionResultDetail.option1,
            option2 = questionResultDetail.option2,
            selectedOption = questionResultDetail.selectedOption
        )
    }
}
@Composable
fun ResultQuestionOption(
    option1: OptionData,
    option2: OptionData,
    selectedOption: RadioButtonOption

) {
    val option1Color = when (option1.mbtiType.name) {
        "E", "S", "T", "J" -> colorResource(R.color.bar_estj)
        else -> colorResource(R.color.bar_infp)
    }
    val option2Color = when (option2.mbtiType.name) {
        "E", "S", "T", "J" -> colorResource(R.color.bar_estj)
        else -> colorResource(R.color.bar_infp)
    }
    Box(
        modifier = Modifier.height(120.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        OptionRadioButton(
            selected = selectedOption == RadioButtonOption.OPTION_1,
            text = option1.optionText,
            color = colorResource(R.color.option1_button),
            selectedColor = colorResource(R.color.selected_option1_button),

        )

        MbtiText(
            text = option1.mbtiType.name,
            fontSize = 48.sp,
            color = option1Color,
            modifier = Modifier
                .align(Alignment.TopStart)
                .graphicsLayer(
                    translationY = -100f,
                    translationX = 20f,
                    rotationZ = 10f
                )
        )
    }

    Text(
        text = "VS",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )

    Box(
        modifier = Modifier.height(120.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        OptionRadioButton(
            selected = selectedOption == RadioButtonOption.OPTION_2,
            text = option2.optionText,
            color = colorResource(R.color.option2_button),
            selectedColor = colorResource(R.color.selected_option2_button),
        )

        MbtiText(
            text = option2.mbtiType.name,
            fontSize = 48.sp,
            color = option2Color,
            modifier = Modifier
                .align(Alignment.TopStart)
                .graphicsLayer(
                    translationY = -105f,
                    translationX = 20f,
                    rotationZ = 10f
                )
        )
    }



    OtherRadioButton(
        selected = selectedOption == RadioButtonOption.OTHER,
    )
}

@Preview(showBackground = true)
@Composable
fun ResultsByQuestionDetailScreenPreview() {
    // 임의의 더미 데이터 생성
    val dummyQuestionResultDetail = QuestionResultDetail(
        questionText = "영화 속에서 뜬금없이 나온 슬픈 장면, 하지만 얼마전 내가 겪은 상황과 비슷하다.",
        option1 =  OptionData("나 20분 정도 늦을 거 같아. 정말 미안한데 돈 줄 테니까 가페에서 조금만 기다려 줄 수 있어?", MbtiType.E),
        option2 =  OptionData("2번", MbtiType.I),
        selectedOption =  RadioButtonOption.OPTION_1,
    )

    MbtiTestAppTheme {
        Scaffold(
            topBar = {
                MbitTopAppBar(
                    title = stringResource(MbtiTestMenuDestination.titleRes),
                    canNavigateBack = true,
                )
            },
        ) { innerPadding ->
            ResultsByQuestionDetailBody(
                uiState = ResultsByQuestionDetailUiState(dummyQuestionResultDetail),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
}