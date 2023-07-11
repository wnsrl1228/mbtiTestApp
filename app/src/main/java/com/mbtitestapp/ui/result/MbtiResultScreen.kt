package com.mbtitestapp.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mbtitestapp.R
import com.mbtitestapp.navigation.NavigationDestination
import com.mbtitestapp.ui.select.RadioButtonOption
import com.mbtitestapp.ui.select.SelectViewModel
import com.mbtitestapp.ui.theme.MbtiTestAppTheme

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
                .wrapContentSize(Alignment.Center)
        )
    }
}
@Composable
fun MbtiResultBody(
    viewModel: SelectViewModel,
    modifier: Modifier = Modifier,
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .padding(horizontal = 32.dp),
    ) {
        Text(text = "- " + uiState.selectedOptions.size)
        Text(text = "- " + viewModel.getMbtiTestResult())


        for (i in uiState.selectedOptions) {
            Text(text = i.name)
        }


        Button(onClick = { viewModel.setCurrentSelectedOption(1, RadioButtonOption.OPTION_1) }) {

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