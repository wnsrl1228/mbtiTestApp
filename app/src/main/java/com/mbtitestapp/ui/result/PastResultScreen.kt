package com.mbtitestapp.ui.result

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.mbtitestapp.ui.select.SelectBody
import com.mbtitestapp.ui.select.SelectUiState
import com.mbtitestapp.ui.theme.MbtiTestAppTheme
import java.util.Date

object PastResultDestination : NavigationDestination {
    override val route = "past_result"
    override val titleRes = R.string.app_name
}

@Composable
fun PastResultScreen(
    viewModel: PastResultViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    navigateToMbtiResult: (Long) -> Unit,
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
        PastResultBody(
            uiState = viewModel.uiState.collectAsState().value,
            onItemClick = navigateToMbtiResult,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun PastResultBody(
    uiState: PastResultUiState,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier // topbar 가리는 문제 해결
    ) {
        itemsIndexed(uiState.pastResultDataList) { index, pastResultData ->
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp , Color.LightGray),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 32.dp)
                    .clickable {
                        onItemClick(pastResultData.mbtiResultId)
                    }

            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
//                    modifier = modifier
//                        .padding(horizontal = 32.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(18.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "${pastResultData.createdDate}",
                        )
                        Text(
                            text = "${pastResultData.testType}",
                        )
                    }

                    Text(
                        text = "${pastResultData.mbti}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${pastResultData.name}",
                    )
                    Spacer(modifier = Modifier)
                }

            }
        }
    }
}

@Preview
@Composable
fun PastResultBodyPreview() {
    val dummyPastResultDataList = listOf(
        PastResultData(1, "ISNP", "참신한 도전가", "전체 테스트", "2023.07.13"),
        PastResultData(2, "ISNP", "참신한 도전가", "전체 테스트", "2023.07.13"),
    )

    PastResultUiState()
    MbtiTestAppTheme {
        Scaffold(
            topBar = {
                MbitTopAppBar(
                    title = stringResource(MbtiTestMenuDestination.titleRes),
                    canNavigateBack = true,
                    navigateUp = { },
                )
            },
        ) { innerPadding ->
            PastResultBody(
                uiState = PastResultUiState(dummyPastResultDataList),
                onItemClick = {},
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }

    }
}