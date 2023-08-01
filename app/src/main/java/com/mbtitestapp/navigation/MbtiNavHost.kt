package com.mbtitestapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mbtitestapp.ui.AppViewModelProvider
import com.mbtitestapp.ui.home.HomeDestination
import com.mbtitestapp.ui.home.HomeScreen
import com.mbtitestapp.ui.menu.MbtiTestMenuDestination
import com.mbtitestapp.ui.menu.MbtiTestMenuScreen
import com.mbtitestapp.ui.result.MbtiResultDestination
import com.mbtitestapp.ui.result.MbtiResultScreen
import com.mbtitestapp.ui.result.ResultsByQuestionDestination
import com.mbtitestapp.ui.result.ResultsByQuestionDetailDestination
import com.mbtitestapp.ui.result.ResultsByQuestionDetailScreen
import com.mbtitestapp.ui.result.ResultsByQuestionScreen
import com.mbtitestapp.ui.select.SelectDestination
import com.mbtitestapp.ui.select.SelectScreen
import com.mbtitestapp.ui.select.SelectViewModel

@Composable
fun MbtiNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    // SelectViewModel를 mbtiTest화면과 결과화면에서 공유하여 사용
//    val viewModel: SelectViewModel = viewModel(factory = AppViewModelProvider.Factory)

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
                navigateToSelect = {navController.navigate("${SelectDestination.route}/${it}")}
            )
        }

        composable(
            route = SelectDestination.routeWithArgs,
            arguments = listOf(navArgument(SelectDestination.mbtiCategoryArg) {
                type = NavType.StringType
            })) { backStackEntry ->
            SelectScreen(
                navigateToMbtiResult = {navController.navigate(MbtiResultDestination.route)},
                navigateToHome = {navController.popBackStack(HomeDestination.route, false)},
                navBackStackEntry = backStackEntry
            )
        }

//        composable(route = MbtiResultDestination.route) {
//            MbtiResultScreen(
//                viewModel = viewModel,
//                navigateToHome = {navController.popBackStack(HomeDestination.route, false)},
//                naviagteToResultsByQuestion = {navController.navigate(ResultsByQuestionDestination.route)}
//            )
//        }
//
//        composable(route = ResultsByQuestionDestination.route) {
//            ResultsByQuestionScreen(
//                viewModel = viewModel,
//                navigateBack = {navController.popBackStack()},
//                navigateToResultsByQuestionDetail = { navController.navigate("${ResultsByQuestionDetailDestination.route}/${it}") },
//            )
//        }
//
//        composable(
//            route = ResultsByQuestionDetailDestination.routeWithArgs,
//            arguments = listOf(navArgument(ResultsByQuestionDetailDestination.itemIdArg) {
//                type = NavType.IntType
//            })
//        ) {backStackEntry ->
//            ResultsByQuestionDetailScreen(
//                viewModel = viewModel,
//                navigateBack = {navController.popBackStack()},
//                navBackStackEntry = backStackEntry
//            )
//        }

    }
}