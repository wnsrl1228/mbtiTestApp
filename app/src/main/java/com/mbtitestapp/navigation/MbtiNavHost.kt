package com.mbtitestapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mbtitestapp.data.DataSource.questionDataList
import com.mbtitestapp.ui.home.HomeDestination
import com.mbtitestapp.ui.home.HomeScreen
import com.mbtitestapp.ui.menu.MbtiTestMenuDestination
import com.mbtitestapp.ui.menu.MbtiTestMenuScreen
import com.mbtitestapp.ui.select.SelectDestination
import com.mbtitestapp.ui.select.SelectScreen

@Composable
fun MbtiNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToMbtiTestMenu = {navController.navigate(MbtiTestMenuDestination.route)}
            )
        }

        composable(route = MbtiTestMenuDestination.route) {
            MbtiTestMenuScreen(
                navigateBack = {navController.popBackStack()},
                navigateToSelect = {navController.navigate(SelectDestination.route)}
            )
        }

        composable(route = SelectDestination.route) {
            SelectScreen(
                options = questionDataList
            )
        }

    }
}