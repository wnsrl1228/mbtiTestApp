package com.mbtitestapp.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mbtitestapp.MbitTopAppBar
import com.mbtitestapp.R
import com.mbtitestapp.data.MbtiCategory
import com.mbtitestapp.navigation.NavigationDestination
import com.mbtitestapp.ui.home.MenuButton
import com.mbtitestapp.ui.theme.MbtiTestAppTheme

object MbtiTestMenuDestination : NavigationDestination {
    override val route = "mbti_test_menu"
    override val titleRes = R.string.app_name

}
@Composable
fun MbtiTestMenuScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    navigateToSelect: (String) -> Unit
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
        MbtiTestMenuBody(
            onMbtiTestStartButtonClick = navigateToSelect,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun MbtiTestMenuBody(
    modifier: Modifier,
    onMbtiTestStartButtonClick: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.mbti_logo_big),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        MenuButton(
            onClick = {onMbtiTestStartButtonClick(MbtiCategory.ALL.name)},
            text = "테스트 시작하기"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MenuButton(
                text = "I E",
                onClick = {onMbtiTestStartButtonClick(MbtiCategory.IE.name)},
                modifier = Modifier.weight(1f),
            )
            MenuButton(
                text = "S N",
                onClick = {onMbtiTestStartButtonClick(MbtiCategory.SN.name)},
                modifier = Modifier.weight(1f),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MenuButton(
                text = "T F",
                onClick = {onMbtiTestStartButtonClick(MbtiCategory.TF.name)},
                modifier = Modifier.weight(1f),
            )
            MenuButton(
                text = "P J",
                onClick = {onMbtiTestStartButtonClick(MbtiCategory.PJ.name)},
                modifier = Modifier.weight(1f),
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MbtiTestAppTheme {
        MbtiTestMenuScreen(navigateBack = { /*Do nothing*/ },navigateToSelect = {} )
    }
}