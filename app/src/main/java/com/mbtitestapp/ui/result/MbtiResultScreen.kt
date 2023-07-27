package com.mbtitestapp.ui.result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.mbtitestapp.R
import com.mbtitestapp.navigation.NavigationDestination
import com.mbtitestapp.ui.select.SelectViewModel
import com.mbtitestapp.ui.theme.MbtiTestAppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.mbtitestapp.data.Mbti
import com.mbtitestapp.data.MbtiTestResultInfo
import com.mbtitestapp.data.result.MbtiInfo
import com.mbtitestapp.ui.home.MenuButton
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

object MbtiResultDestination : NavigationDestination {
    override val route = "mbti_result"
    override val titleRes = R.string.app_name
}

@Composable
fun MbtiResultScreen (
    modifier: Modifier = Modifier,
    viewModel: SelectViewModel,
    navigateToHome: () -> Unit,
    naviagteToResultsByQuestion: () -> Unit
) {

    var showAlertDialog by remember { mutableStateOf(false) }    // 팝업 창을 표시할 컴포저블 UI
    if (showAlertDialog) {
        AlertDialog(
            title = {
                Text(text = "홈 화면으로 이동합니다.")
            },
            text = {
                Text(text = "테스트 결과는 저장되지 않습니다.")
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

    val mbtiTestResultInfo: MbtiTestResultInfo = viewModel.getMbtiTestResultInfo()
    val mbtiInfo = remember { mutableStateOf<MbtiInfo?>(null) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            mbtiInfo.value = viewModel.getMbtiInfo(mbtiTestResultInfo.mbti).first()
        }
    }

    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        MbtiResultBody(
            navigateToHome = {
                viewModel.resetSelectUiState()
                navigateToHome()
            },
            naviagteToResultsByQuestion = naviagteToResultsByQuestion,
            mbtiTestResultInfo = mbtiTestResultInfo,
            mbtiInfo = mbtiInfo.value,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}
@Composable
fun MbtiResultBody(
    navigateToHome: () -> Unit,
    naviagteToResultsByQuestion: () -> Unit,
    mbtiTestResultInfo: MbtiTestResultInfo,
    mbtiInfo: MbtiInfo?,
    modifier: Modifier = Modifier,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()), // 스크롤 https://developer.android.com/jetpack/compose/gestures?hl=ko#scrolling
    ) {
        Text(
            text = "<결과>",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 64.dp)
        )

        Text(
            text = mbtiTestResultInfo.mbti.name + "\n" + mbtiInfo?.name,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            modifier = Modifier.height(100.dp)
        )

        Text(
            text = mbtiInfo?.detailedDesc ?: "",
            fontSize = 16.sp,
            lineHeight = 1.5.em,
        )

        BarChart(
            maxHeight = defaultMaxHeight,
            values = mbtiTestResultInfo.scores,
            mbtiResult = mbtiTestResultInfo.mbti
        )

        MenuButton(
            onClick = naviagteToResultsByQuestion,
            text = "질문별 결과보기",
        )
        MenuButton(
            onClick = navigateToHome,
            text = "홈으로 돌아가기",
        )

        Spacer(modifier = Modifier.padding(bottom = 30.dp))
    }
}

private val defaultMaxHeight = 300.dp
private const val MAX_SCORE = 27
private val SCORE_LABELS = listOf("27", "14", "0", "14", "27")
private val INTENSITY_LABELS = listOf("매우", "보통", "약간", "약간", "보통", "매우")
@Composable
internal fun BarChart(
    modifier: Modifier = Modifier,
    values: List<Int>,
    mbtiResult: Mbti,
    maxHeight: Dp = defaultMaxHeight  // 차트 높이
) {

//    assert(values.isNotEmpty()) { "Input values are empty" }

    val borderColor = Color.DarkGray
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }

    Column(
        modifier = Modifier
    ) {
        AxisLabels(INTENSITY_LABELS)

        Column(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .height(maxHeight)
                    .drawBehind {
                        // draw X-top-Axis
                        drawLine(
                            color = borderColor,
                            start = Offset(0f, 0f), // 가로에서 화면 가운데로 변경
                            end = Offset(size.width, 0f),
                            strokeWidth = strokeWidth
                        )
                        // draw X-bottom-Axis
                        drawLine(
                            color = borderColor,
                            start = Offset(0f, size.height), // 가로에서 화면 가운데로 변경
                            end = Offset(size.width, size.height),
                            strokeWidth = strokeWidth
                        )
                    }
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            values.forEachIndexed { index, value ->

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Bar(
                        score = value,
                        index = index,
                        mbtiResult = mbtiResult,
                    )

                    val ESTJ = listOf("E", "S", "T", "J")
                    val INFP = listOf("I", "N", "F", "P")
                    MbtiText(
                        text = ESTJ[index],
                        modifier = Modifier.align(Alignment.CenterStart),
                        color = colorResource(R.color.bar_estj)
                    )
                    MbtiText(
                        text = INFP[index],
                        modifier = Modifier.align(Alignment.CenterEnd),
                        colorResource(R.color.bar_infp)
                    )

                }
            }
        }

        AxisLabels(SCORE_LABELS)
    }


}

@Composable
fun MbtiText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    fontSize: TextUnit = 32.sp
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        color = color,
        style = TextStyle(
            shadow = Shadow(
                color = Color.Black,
                offset = Offset(5f, 0f),
                blurRadius = 0.5f
            )
        )
    )
}
/**
 * 차트의 bar에 대한 컴포저블
 */
@Composable
private fun Bar(
    score: Int,
    index: Int,
    mbtiResult: Mbti,
    modifier: Modifier = Modifier
) {

    val isESJT: Boolean = mbtiResult.name[index] in listOf('E', 'S', 'T', 'J')
    val color = if (isESJT) colorResource(R.color.bar_estj) else colorResource(R.color.bar_infp)
    BoxWithConstraints {

        val barMaxWidth = (this@BoxWithConstraints.maxWidth - 40.dp) / 2  // 바 길이(폰 크기마다 다름)
        val ratio: Float = score / MAX_SCORE.toFloat()  // score로 bar 길이 비율 측정
        val barWidth = barMaxWidth * ratio              // 계산된 bar 길이

        Box (
            modifier = Modifier.width(IntrinsicSize.Max), // bar 크기에 맞게 지정
            contentAlignment = Alignment.Center
        ) {
            /**
             * 막대 바
             */
            Spacer(
                modifier = Modifier
                    .padding( // mbti 타입에 따라 ESTJ는 end 패딩, INFP일 경우 start 패딩
                        start = if (isESJT) 0.dp else barWidth,
                        end = if (isESJT) barWidth else 0.dp
                    )
                    .padding(vertical = 10.dp)
                    .height(50.dp)
                    .width(barWidth)  // 막대 가로 길이 = mbti 점수
                    .background(color)
                    .border(width = 1.dp, color = Color.Black)

            )
            /**
             * 숫자 표시
             * - 숫자 데이터와, 숫자표시 위치값이 있어야 됨
             */
            Text(
                text = score.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(
                        if (isESJT) Alignment.CenterStart else Alignment.CenterEnd
                    )
                    .padding(start = if (isESJT) 2.dp else 0.dp, end = if (isESJT) 0.dp else 2.dp)
            )
        }
    }

}
/**
 * 차트 라벨
 */
@Composable
private fun AxisLabels(
    texts: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.padding_small),
                vertical = dimensionResource(R.dimen.padding_extra_small)
            )
    ) {
        texts.forEach { text ->
            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MbtiResultScreenPreview() {



    val mbtiInfo = MbtiInfo(Mbti.ENFJ,"친절하고 활동적인 교양가", "타인의 성장을 돕고 사회적인 환경을 조성하는 리더십을 가지고 있습니다.",
    "ENFJ 유형은 친절하고 활동적인 교양가로, 타인의 성장을 돕고 사회적인 환경을 조성하는 리더십을 가지고 있습니다.")
    MbtiTestAppTheme {
        Scaffold(
        ) { innerPadding ->
            MbtiResultBody(
                navigateToHome = {}, naviagteToResultsByQuestion = {},
                mbtiTestResultInfo = MbtiTestResultInfo(listOf(27,20,3,20), Mbti.ENFJ),
                mbtiInfo =  mbtiInfo,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }

    }
}