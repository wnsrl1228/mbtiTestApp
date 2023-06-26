package com.mbtitestapp.ui.select

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.mbtitestapp.R
import com.mbtitestapp.data.QuestionData
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
    options: List<QuestionData>
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        SelectBody(
            options = options,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun SelectBody(
    options: List<QuestionData>,
    modifier: Modifier = Modifier
) {

    var selectedOption by remember { mutableStateOf(RadioButtonOption.NONE) } // 현재 선택된 버튼

    var currentQuestionNum by remember { mutableStateOf(0) } // 현재 선택된 버튼
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .padding(horizontal = 32.dp),
    ) {
        Text(
            text = "${currentQuestionNum+1}/${options.size}",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = options[currentQuestionNum].questionText,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            lineHeight = 1.5.em,
            modifier = Modifier.height(100.dp)
        )

        OptionRadioButton(
            selected = selectedOption == RadioButtonOption.OPTION_1,
            onOptionSelected = { selectedOption = RadioButtonOption.OPTION_1 },
            text = options[currentQuestionNum].option1.first
        )

        Text(
            text = "VS",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        OptionRadioButton(
            selected = selectedOption == RadioButtonOption.OPTION_2,
            onOptionSelected = { selectedOption = RadioButtonOption.OPTION_2 },
            text = options[currentQuestionNum].option1.first
        )
        OtherRadioButton(
            selected = selectedOption == RadioButtonOption.OTHER,
            onOptionSelected = { selectedOption = RadioButtonOption.OTHER }
        )


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { currentQuestionNum-- },
                enabled = currentQuestionNum > 0,
                modifier = Modifier.width(100.dp)
            ) {
                Text(text = "이전")
            }

            Button(
                onClick = { currentQuestionNum++ },
                enabled = currentQuestionNum < options.size - 1,
            ) {
                Text(text = "다음")
            }
        }
    }
}

@Composable
fun OptionRadioButton(
    selected: Boolean,
    onOptionSelected: () -> Unit,
    text: String
) {
    Box(
        Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(if (selected) Color.DarkGray else Color.LightGray)
            .clickable { onOptionSelected() }
    ) {
        Box(
            Modifier
                .align(Alignment.Center)
                .background(Color.Black)
                .sizeIn(0.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.background(color = Color.White)
            )
        }
    }
}

@Composable
fun OtherRadioButton(
    selected: Boolean,
    onOptionSelected: () -> Unit,
) {
    Box(
        Modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(if (selected) Color.DarkGray else Color.LightGray)
            .clickable { onOptionSelected() }
    ) {
        Box(
            Modifier
                .align(Alignment.Center)
                .background(Color.Black)
                .sizeIn(0.dp)
        ) {
            Text(
                text = "둘다 아닌거 같아요.",
                modifier = Modifier.background(color = Color.White)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MbtiTestAppTheme {
        SelectScreen(options = listOf())
    }
}