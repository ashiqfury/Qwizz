package com.example.qwizz.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.qwizz.model.Question
import com.example.qwizz.ui.screens.DifficultyScreen
import com.example.qwizz.ui.screens.HomeScreen
import com.example.qwizz.ui.screens.QuestionScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QwizzNavHost(
    navController: NavHostController,
    questions: List<Question>
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = QScreens.Home.route
    ) {

        composable(
            route = QScreens.Home.route,
            enterTransition = NavAnimation.enterTransition,
            exitTransition = NavAnimation.exitTransition,
            popEnterTransition = NavAnimation.popEnterTransition,
            popExitTransition = NavAnimation.popExitTransition
        ) {
            HomeScreen(navController)
        }

        composable(
            route = QScreens.Difficulty.route,
            enterTransition = NavAnimation.enterTransition,
            exitTransition = NavAnimation.exitTransition,
            popEnterTransition = NavAnimation.popEnterTransition,
            popExitTransition = NavAnimation.popExitTransition
        ) {
            DifficultyScreen(navController)
        }

        composable(
            route = QScreens.Question.route,
            enterTransition = NavAnimation.enterTransition,
            exitTransition = NavAnimation.exitTransition,
            popEnterTransition = NavAnimation.popEnterTransition,
            popExitTransition = NavAnimation.popExitTransition
        ) {
            QuestionScreen(navController, questions)
        }
    }
}