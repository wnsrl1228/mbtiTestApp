package com.mbtitestapp.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mbtitestapp.R
import com.mbtitestapp.navigation.NavigationDestination
import com.mbtitestapp.ui.select.SelectViewModel
import com.mbtitestapp.ui.theme.MbtiTestAppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.mbtitestapp.data.MbtiEnum
import com.mbtitestapp.data.MbtiTestResultInfo

object MbtiResultDestination : NavigationDestination {
    override val route = "mbti_result"
    override val titleRes = R.string.app_name
}

@Composable
fun MbtiResultScreen (
    modifier: Modifier = Modifier,
    viewModel: SelectViewModel,
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        MbtiResultBody(
            viewModel = viewModel,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}
@Composable
fun MbtiResultBody(
    viewModel: SelectViewModel,
    modifier: Modifier = Modifier,
) {

    val uiState by viewModel.uiState.collectAsState()
    val mbtiTestResultInfo: MbtiTestResultInfo = viewModel.getMbtiTestResultInfo()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()), // 스크롤 https://developer.android.com/jetpack/compose/gestures?hl=ko#scrolling
    ) {
        Text(
            text = "결과",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 64.dp)
        )

        Text(
            text = mbtiTestResultInfo.mbtiEnum.name + "\n 현실주의 파괴자",
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            modifier = Modifier.height(100.dp)
        )

        Text(
            text = "온화하고 적극적이며 책임감이 강하고 사교성이 풍부하고 동정심이 많다." +
                    " 상당히 이타적이고 민첩하고 인화를 중요시하며 참을성이 많다. " +
                    "다른 사람들의 생각이나 의견에 진지한 관심을 가지고... 자세히 보기 ",
            fontSize = 14.sp,
            lineHeight = 1.5.em,
        )

        BarChart(
            maxHeight = defaultMaxHeight,
            values = mbtiTestResultInfo.scores,
            mbtiResult = mbtiTestResultInfo.mbtiEnum
        )

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)) {
            Text(text = "질문별 결과보기")
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)) {
            Text(text = "홈으로 돌아가기")
        }
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
    mbtiResult: MbtiEnum,
    maxHeight: Dp = defaultMaxHeight  // 차트 높이
) {

//    assert(values.isNotEmpty()) { "Input values are empty" }

    val borderColor = Color.Red
    val density = LocalDensity.current
    val strokeWidth = with(density) { 2.dp.toPx() }

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
                    Text(
                        text = ESTJ[index],
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = INFP[index],
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        }

        AxisLabels(SCORE_LABELS)
    }


}

/**
 * 차트의 bar에 대한 컴포저블
 */
@Composable
private fun Bar(
    score: Int,
    index: Int,
    mbtiResult: MbtiEnum,
    modifier: Modifier = Modifier
) {

    val isESJT: Boolean = mbtiResult.name[index] in listOf('E', 'S', 'T', 'J')

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
                    .background(Color.Magenta)

            )
            /**
             * 숫자 표시
             * - 숫자 데이터와, 숫자표시 위치값이 있어야 됨
             */
            Text(
                text = score.toString(),
                modifier = Modifier.align(
                    if (isESJT) Alignment.CenterStart else Alignment.CenterEnd
                )
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
            .padding(horizontal = 8.dp)
    ) {
        texts.forEach { text ->
            Text(text = text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MbtiTestAppTheme {
        MbtiResultScreen(viewModel = viewModel())
    }
}