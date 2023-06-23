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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mbtitestapp.MbitTopAppBar
import com.mbtitestapp.R
import com.mbtitestapp.navigation.NavigationDestination
import com.mbtitestapp.ui.home.HomeBody
import com.mbtitestapp.ui.home.HomeScreen
import com.mbtitestapp.ui.theme.MbtiTestAppTheme

object MbtiTestMenuDestination : NavigationDestination {
    override val route = "mbti_test_menu"
    override val titleRes = R.string.app_name

}
@Composable
fun MbtiTestMenuScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun MbtiTestMenuBody(modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)

        ) {
            Text(text = "테스트 시작하기")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {  },
            ) {
                Text(text = "I E")
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {  },
            ) {
                Text(text = "S N")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {  },
            ) {
                Text(text = "T F")
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {  },
            ) {
                Text(text = "P J")
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MbtiTestAppTheme {
        MbtiTestMenuScreen(navigateBack = { /*Do nothing*/ })
    }
}