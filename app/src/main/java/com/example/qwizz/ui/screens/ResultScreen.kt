package com.example.qwizz.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.qwizz.R
import com.example.qwizz.ui.navigation.QScreens
import com.example.qwizz.ui.theme.QColors
import com.example.qwizz.ui.utils.QShapes
import com.example.qwizz.ui.utils.StatusBarInsetHandler
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@Composable
internal fun ResultScreen(
    navController: NavController
) {

    /*var backPressed by remember { mutableStateOf(false) }
    BackHandler {
        backPressed = true
    }
    if (backPressed) {
        Snackbar {
            Text(text = "Sorry! Going back is not allow here!")
        }
        backPressed = false
    }*/

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        backgroundColor = QColors.LightWhite,
        drawerBackgroundColor = QColors.LightWhite,
        drawerElevation = 8.dp,
//        drawerShape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
        drawerShape = QShapes.DrawerShapes.shape
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        val brush = Brush.linearGradient(
            colors = listOf(
                Color.White,
                QColors.Golden.copy(0.05f),
                Color.White
            )
        )
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(brush),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StatusBarInsetHandler {}
            ResultTitle()
            BannerImage()
            PointsText()
            DetailsSection()
            HomeButton(navController = navController)
        }
    }
}

@Composable
fun ResultTitle() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Congratulations!",
            color = QColors.Golden,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 40.dp)
        )
    }
}


@Composable
private fun BannerImage() {
    Image(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(bottom = 15.dp),
        painter = painterResource(id = R.drawable.ic_winner),
        contentDescription = "result_banner"
    )
}

@Composable
private fun PointsText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Points: ",
            color = QColors.TextPrimary,
            fontSize = 32.sp,
        )
        Text(
            text = "308",
            color = QColors.Golden,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
        )
    }
}

@Composable
private fun DetailsSection() {
    Column(modifier = Modifier
        .fillMaxWidth(0.8f)
        .padding(top = 20.dp)) {
        DetailText(text = "Correct Answered", value = 7)
        DetailText(text = "Wrong Answered", value = 1)
        DetailText(text = "Skipped Answered", value = 2)
        DetailText(text = "Time Taken (in secs)", value = 102)
    }
}

@Composable
private fun DetailText(
    text: String,
    value: Int,
) {
    val decoratedValue = value.prefixZero()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$text: ",
            color = QColors.TextSecondary,
            fontSize = 18.sp,
        )
        Text(
            text = decoratedValue,
            color = QColors.Saffron,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
    }
}

@Composable
private fun HomeButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(QScreens.Home.route) {
                popUpTo(route = QScreens.Home.route)
                launchSingleTop = true
            }
        },
        modifier = Modifier.fillMaxWidth(0.8f),
        colors = ButtonDefaults.textButtonColors()
    ) {
        Text(text = "Take back to Home", color = QColors.BabyGreen)
    }
}

private fun Int.prefixZero(): String {
    return if (this.toString().length == 1) "0$this" else this.toString()
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    val controller = rememberAnimatedNavController()
    ResultScreen(navController = controller)
}