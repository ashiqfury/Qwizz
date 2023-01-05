package com.example.qwizz.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.qwizz.R
import com.example.qwizz.ui.navigation.QScreens
import com.example.qwizz.ui.theme.QColors
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@Composable
internal fun DifficultyScreen(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = QColors.LightWhite,
        topBar = {
            DifficultyScreenTopBar(navController)
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 20.dp), // extra space at bottom of the screen
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DifficultyTitle()
            BannerImage()
            DifficultyButtons(navController)
        }
    }
}

@Composable
private fun DifficultyScreenTopBar(navController: NavController) {
    TopAppBar(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
        backgroundColor = QColors.LightWhite,
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = QColors.TextPrimary
                )
            }
        },
        elevation = 0.dp,
        title = {
            Text(
                text = stringResource(R.string.back),
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 18.sp,
                color = QColors.TextPrimary
            )
        }
    )
}

@Composable
private fun DifficultyTitle() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        text = "Science",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 26.sp,
        color = QColors.TextPrimary,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun BannerImage() {
    Image(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 25.dp),
        painter = painterResource(id = R.drawable.reading),
        contentDescription = "Subject_Banner"
    )
}


@Composable
private fun DifficultyButtons(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 30.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            text = "Choose your level",
            fontSize = 16.sp,
            color = QColors.TextSecondary,
        )
        Difficulty.values().forEach { difficulty ->
            DifficultyButton(difficulty, navController)
        }
    }
}

@Composable
private fun DifficultyButton(
    difficulty: Difficulty,
    navController: NavController
) {
    val interactionSource = remember { MutableInteractionSource() }
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .indication(
                indication = rememberRipple(),
                interactionSource = interactionSource
            ),
        shape = CircleShape,
        border = BorderStroke(1.dp, QColors.ProgressBarBG),
        onClick = {
            navController.navigate(route = QScreens.Question.route) {
//                popUpTo(QwizzScreens.Home.route)
            }
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = QColors.LightWhite)
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = difficulty.value,
            fontSize = 14.sp,
            color = QColors.TextPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
private fun DifficultyScreenPreview() {
    val navController = rememberAnimatedNavController()
    DifficultyScreen(navController = navController)
}

private enum class Difficulty(val value: String) {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard")
}