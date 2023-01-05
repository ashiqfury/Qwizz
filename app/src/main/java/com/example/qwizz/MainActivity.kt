package com.example.qwizz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.qwizz.ui.navigation.QwizzNavHost
import com.example.qwizz.ui.screens.DifficultyScreen
import com.example.qwizz.ui.screens.HomeScreen
import com.example.qwizz.ui.screens.QuestionScreen
import com.example.qwizz.ui.theme.QwizzTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            QwizzTheme {
                val navHostController = rememberAnimatedNavController()
//                QuestionScreen(navController = navHostController)
                QwizzNavHost(navController = navHostController)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QwizzTheme {

    }
}