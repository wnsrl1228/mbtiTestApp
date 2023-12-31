package com.mbtitestapp.ui.select

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mbtitestapp.R
import com.mbtitestapp.data.MbtiCategory
import com.mbtitestapp.data.MbtiType
import com.mbtitestapp.navigation.NavigationDestination
import com.mbtitestapp.ui.AppViewModelProvider
import com.mbtitestapp.ui.theme.MbtiTestAppTheme
import kotlinx.coroutines.launch

enum class RadioButtonOption {
    NONE,
    OPTION_1,
    OPTION_2,
    OTHER
}

object SelectDestination : NavigationDestination {
    override val route = "select"
    override val titleRes = R.string.app_name
    const val mbtiCategoryArg = "ALL"
    val routeWithArgs = "${SelectDestination.route}/{$mbtiCategoryArg}"
}

@Composable
fun SelectScreen(
    modifier: Modifier = Modifier,
    navigateToMbtiResult: (Long) -> Unit,
    navigateToHome: () -> Unit,
    viewModel: SelectViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    var showAlertDialog by remember { mutableStateOf(false) }    // 팝업 창을 표시할 컴포저블 UI
    if (showAlertDialog) {
        AlertDialog(
            title = {
                Text(text = "테스트를 종료하시겠습니까?")
            },
            text = {
                Text(text = "지금까지 진행된 테스트는 저장되지 않습니다.")
            },
            onDismissRequest = { showAlertDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetSelectUiState()
                    navigateToHome()
                }) {
                    Text(text = "확인")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAlertDialog = false }) {
                    Text(text = "취소")
                }
            }
        )
    }
    BackHandler(enabled = true) {
        showAlertDialog = true
    }

    val selectUiState by viewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        SelectBody(
            selectUiState = selectUiState,
            onOptionSelected = viewModel.setCurrentSelectedOption,
            onMbtiResultButtonClick = {
                coroutineScope.launch {
                    navigateToMbtiResult(viewModel.addMbtiResult())
                    viewModel.resetSelectUiState()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
        )
    }
}

@Composable
fun SelectBody(
    selectUiState: SelectUiState,
    onOptionSelected: (Int, RadioButtonOption) -> Unit,
    onMbtiResultButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val questionDataList = selectUiState.questionDataList                 // mbti 테스트 관련 데이터
    var currentQuestionNum by remember { mutableStateOf(0) }        // 현재 선택된 버튼

    val currentSelectedOption = questionDataList.getOrNull(currentQuestionNum)?.selectedOption ?: RadioButtonOption.NONE

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .padding(horizontal = 32.dp),
    ) {

        Text(
            text = "${currentQuestionNum+1}/${questionDataList.size}",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        // 질문지
        Text(
            text = questionDataList.getOrNull(currentQuestionNum)?.questionText ?: "",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            modifier = Modifier.height(105.dp)
        )

        QuestionOption(
            onOptionSelected = onOptionSelected,
            optionText1 = questionDataList.getOrNull(currentQuestionNum)?.option1?.optionText ?: "",
            optionText2 = questionDataList.getOrNull(currentQuestionNum)?.option2?.optionText ?: "",
            currentQuestionNum = currentQuestionNum,
            selectedOption = currentSelectedOption
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            PreviousButton(
                enabled = currentQuestionNum > 0,
                onPreviousClick = {currentQuestionNum--},
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.weight(1f))

            NextOrResultButton(
                enabled = currentSelectedOption != RadioButtonOption.NONE,
                onNextClick = { currentQuestionNum++ },
                onResultClick = onMbtiResultButtonClick,
                isLastQuestion = currentQuestionNum == questionDataList.size - 1,
                modifier = Modifier.weight(1f)
            )

        }
    }
}

@Composable
fun PreviousButton(
    enabled: Boolean,
    onPreviousClick: () -> Unit,
    modifier: Modifier,
) {
    PageButton(
        enabled = enabled,
        onClick = onPreviousClick,
        text = "이전",
        modifier = modifier
    )
}
@Composable
fun NextOrResultButton(
    enabled: Boolean,
    onNextClick: () -> Unit,
    onResultClick: () -> Unit,
    isLastQuestion: Boolean,
    modifier: Modifier
) {
    PageButton(
        enabled = enabled,
        onClick = if (isLastQuestion) onResultClick else onNextClick,
        text = if (isLastQuestion) "결과" else "다음",
        modifier = modifier
    )
}
@Composable
fun PageButton(
    enabled: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {

    val shadowSize = if (enabled) 4.dp else 0.dp

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .shadow(shadowSize, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.page_navigation_button)
        )
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            letterSpacing = 5.sp,
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_extra_small))
        )
    }
}

@Composable
fun QuestionOption(
    onOptionSelected: (Int, RadioButtonOption) -> Unit,
    optionText1: String,
    optionText2: String,
    currentQuestionNum: Int,
    selectedOption: RadioButtonOption

) {

    OptionRadioButton(
        selected = selectedOption == RadioButtonOption.OPTION_1,
        onOptionSelected = {onOptionSelected(currentQuestionNum, RadioButtonOption.OPTION_1)},
        text = optionText1,
        color = colorResource(R.color.option1_button),
        selectedColor = colorResource(R.color.selected_option1_button),
    )

    Text(
        text = "VS",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )

    OptionRadioButton(
        selected = selectedOption == RadioButtonOption.OPTION_2,
        onOptionSelected = {onOptionSelected(currentQuestionNum, RadioButtonOption.OPTION_2)},
        text = optionText2,
        color = colorResource(R.color.option2_button),
        selectedColor = colorResource(R.color.selected_option2_button),
    )

    OtherRadioButton(
        selected = selectedOption == RadioButtonOption.OTHER,
        onOptionSelected = {onOptionSelected(currentQuestionNum, RadioButtonOption.OTHER)}
    )
}

@Composable
fun OptionRadioButton(
    selected: Boolean,
    onOptionSelected: () -> Unit = {},
    text: String,
    color : Color,
    selectedColor : Color,
    height: Dp = 110.dp,
    fontSize: TextUnit = 21.sp
) {
    Box(
        Modifier
            .height(height)
            .fillMaxWidth()
            .background(
                selectedColor,
                shape = RoundedCornerShape(16.dp)
            )
            .graphicsLayer( // 버튼이 눌리는 효과
                translationY = if (selected) 0f else -30f
            )
//            .layout { measurable, constraints -> // graphicsLayer랑 동일한 기능을 해서 주석처리
//                val placeable = measurable.measure(constraints)
//                layout(placeable.width, placeable.height) {
//                    if (selected) {
//                        placeable.placeRelative(0, 0)
//                    } else {
//                        placeable.placeRelative(0, -30)
//                    }
//                }
//            }
            .clickable(   // 클릭 이벤트 제거
                onClick = { onOptionSelected() },
                interactionSource = MutableInteractionSource(),
                indication = null
            )

    ) {
        Box(
            Modifier
                .height(height)
                .fillMaxWidth()
                .background(
                    if (selected) selectedColor
                    else color,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Text(
                text = text,
                fontSize = fontSize,
                color =  if (selected) Color.White else Color.Black,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    .background(
                        if (selected) selectedColor
                        else color
                    )
            )
        }
    }
}

@Composable
fun OtherRadioButton(
    selected: Boolean,
    onOptionSelected: () -> Unit = {},
) {

    OptionRadioButton(
        selected = selected,
        onOptionSelected = onOptionSelected,
        text = "둘 다 아닌거 같아요.",
        color =  Color.LightGray,
        selectedColor = colorResource(R.color.selected_other_button),
        height = 50.dp,
        fontSize = 18.sp
    )
}

@Preview(showBackground = true)
@Composable
fun SelectBodyPreview() {
    val dummyQuestionDataList = listOf(
        QuestionData(1, "안녕1", MbtiCategory.PJ, OptionData("1번", MbtiType.E), OptionData("2번", MbtiType.E), RadioButtonOption.OPTION_1),
        QuestionData(2, "안녕2", MbtiCategory.PJ, OptionData("1번", MbtiType.E), OptionData("2번", MbtiType.I), RadioButtonOption.OPTION_1),
        QuestionData(3, "안녕2", MbtiCategory.PJ, OptionData("1번", MbtiType.F), OptionData("2번", MbtiType.T), RadioButtonOption.OPTION_1),
        QuestionData(4, "안녕2", MbtiCategory.PJ, OptionData("1번", MbtiType.T), OptionData("2번", MbtiType.F), RadioButtonOption.OPTION_2),
        // 다른 항목들도 추가
    )

    val dummyOnOptionSelected: (Int, RadioButtonOption) -> Unit = { _, _ -> }

    MbtiTestAppTheme {
        SelectBody(
            selectUiState = SelectUiState(dummyQuestionDataList),
            onOptionSelected = dummyOnOptionSelected,
            onMbtiResultButtonClick = {},
        )
    }
}