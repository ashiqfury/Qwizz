package com.example.qwizz

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.qwizz.data.repository.local.json.getDataFromAssets
import com.example.qwizz.data.repository.local.json.readJSONFromAsset
import com.example.qwizz.data.utils.QuizCategories
import com.example.qwizz.ui.navigation.QwizzNavHost
import com.example.qwizz.ui.screens.HomeScreen
import com.example.qwizz.ui.common.InternetBanner
import com.example.qwizz.ui.screens.QuestionScreen
import com.example.qwizz.ui.theme.QwizzTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            QwizzTheme {
                val navHostController = rememberAnimatedNavController()

                val sportsQuestions = getDataFromAssets(QuizCategories.SPORTS.fileName)
                QuestionScreen(navController = navHostController, questions = sportsQuestions)
//                InternetBanner {
//                    QwizzNavHost(navController = navHostController, questions = sportsQuestions)
//                }
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QwizzTheme {
//        HomeScreen(navController = rememberAnimatedNavController())
    }
}