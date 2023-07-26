package com.mbtitestapp.ui.select

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.mbtitestapp.R
import com.mbtitestapp.navigation.NavigationDestination
import com.mbtitestapp.ui.theme.MbtiTestAppTheme

enum class RadioButtonOption {
    NONE,
    OPTION_1,
    OPTION_2,
    OTHER
}

object SelectDestination : NavigationDestination {
    override val route = "select"
    override val titleRes = R.string.app_name
}

@Composable
fun SelectScreen(
    modifier: Modifier = Modifier,
    navigateToMbtiResult: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: SelectViewModel,
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


    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        SelectBody(
            uiState = viewModel.uiState.collectAsState().value,
            onOptionSelected = viewModel.setCurrentSelectedOption,
            onMbtiResultButtonClick = navigateToMbtiResult,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
        )
    }
}

@Composable
fun SelectBody(
    uiState: SelectUiState,
    onOptionSelected: (Int, RadioButtonOption) -> Unit,
    onMbtiResultButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val questionDataList = uiState.questionDataList                // mbti 테스트 관련 데이터
    val selectedOptions = uiState.selectedOptions                  // mbti 테스트 관련 데이터
    var currentQuestionNum by remember { mutableStateOf(0) } // 현재 선택된 버튼

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

        Text(
            text = questionDataList[currentQuestionNum].questionText,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            modifier = Modifier.height(120.dp)
        )

        QuestionOption(
            onOptionSelected = onOptionSelected,
            optionText1 = questionDataList[currentQuestionNum].option1.optionText,
            optionText2 = questionDataList[currentQuestionNum].option2.optionText,
            currentQuestionNum = currentQuestionNum,
            selectedOption = selectedOptions[currentQuestionNum]
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { currentQuestionNum-- },
                enabled = currentQuestionNum > 0,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "이전")
            }

            Spacer(modifier = Modifier.weight(1f))

            if (currentQuestionNum == questionDataList.size - 1) {
                Button(
                    onClick = onMbtiResultButtonClick,
                    enabled = selectedOptions[currentQuestionNum] != RadioButtonOption.NONE,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "결과 보기")
                }
            } else {
                Button(
                    onClick = {
                        currentQuestionNum++
                    },
                    enabled = currentQuestionNum < questionDataList.size - 1 &&
                            selectedOptions[currentQuestionNum] != RadioButtonOption.NONE,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "다음")
                }
            }

        }
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
        selectedColor = Color.Gray,
        height = 50.dp,
        fontSize = 18.sp
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MbtiTestAppTheme {
    }
}