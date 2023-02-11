package com.example.qwizz.ui.navigation


internal sealed class QScreens(val route: String) {
    object Home: QScreens("home_screen")
    object Difficulty: QScreens("difficulty_screen")
    object Question: QScreens("question_screen")
    object Result: QScreens("result_screen")
}