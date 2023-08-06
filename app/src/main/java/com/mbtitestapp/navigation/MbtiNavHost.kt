package com.mbtitestapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mbtitestapp.ui.home.HomeDestination
import com.mbtitestapp.ui.home.HomeScreen
import com.mbtitestapp.ui.menu.MbtiTestMenuDestination
import com.mbtitestapp.ui.menu.MbtiTestMenuScreen
import com.mbtitestapp.ui.result.MbtiResultDestination
import com.mbtitestapp.ui.result.MbtiResultScreen
import com.mbtitestapp.ui.result.PastResultDestination
import com.mbtitestapp.ui.result.PastResultScreen
import com.mbtitestapp.ui.result.ResultsByQuestionDestination
import com.mbtitestapp.ui.result.ResultsByQuestionDetailDestination
import com.mbtitestapp.ui.result.ResultsByQuestionDetailScreen
import com.mbtitestapp.ui.result.ResultsByQuestionScreen
import com.mbtitestapp.ui.select.SelectDestination
import com.mbtitestapp.ui.select.SelectScreen

@Composable
fun MbtiNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToMbtiTestMenu = {navController.navigate(MbtiTestMenuDestination.route)},
                navigateToPastResult = {navController.navigate(PastResultDestination.route)}
            )
        }

        composable(route = MbtiTestMenuDestination.route) {
            MbtiTestMenuScreen(
                navigateBack = {navController.popBackStack()},
                navigateToSelect = {navController.navigate("${SelectDestination.route}/${it}")}
            )
        }

        composable(route = PastResultDestination.route) {
            PastResultScreen(
                navigateBack = {navController.popBackStack()},
                navigateToMbtiResult = {navController.navigate("${MbtiResultDestination.route}/${it}")},
            )
        }

        composable(
            route = SelectDestination.routeWithArgs,
            arguments = listOf(navArgument(SelectDestination.mbtiCategoryArg) {
                type = NavType.StringType
            })) {

            SelectScreen(
                navigateToMbtiResult = {navController.navigate("${MbtiResultDestination.route}/${it}")},
                navigateToHome = {navController.popBackStack(HomeDestination.route, false)},
            )
        }

        composable(
            route = MbtiResultDestination.routeWithArgs,
            arguments = listOf(navArgument(MbtiResultDestination.mbtiResultIdArg) {
                type = NavType.LongType
            })) {

            MbtiResultScreen(
                navigateToHome = {navController.popBackStack(HomeDestination.route, false)},
                naviagteToResultsByQuestion = {navController.navigate("${ResultsByQuestionDestination.route}/${it}")},
                navController = navController
            )
        }

        composable(
            route = ResultsByQuestionDestination.routeWithArgs,
            arguments = listOf(navArgument(ResultsByQuestionDestination.mbtiResultIdArg) {
                type = NavType.LongType
            })) { backStackEntry ->

            val mbtiResultId = backStackEntry.arguments?.getLong("mbtiResultIdArg")

            ResultsByQuestionScreen(
                navigateBack = {navController.popBackStack()},
                navigateToResultsByQuestionDetail = { navController.navigate("${ResultsByQuestionDetailDestination.route}/${mbtiResultId}/${it}")},
            )
        }

        composable(
            route = ResultsByQuestionDetailDestination.routeWithArgs,
            arguments = listOf(
                navArgument(ResultsByQuestionDetailDestination.mbtiResultIdArg) {
                type = NavType.LongType
            }, navArgument(ResultsByQuestionDetailDestination.questionIdArg) {
                type = NavType.LongType
            })
        ) {backStackEntry ->
            ResultsByQuestionDetailScreen(
                navigateBack = {navController.popBackStack()},
            )
        }

    }
}