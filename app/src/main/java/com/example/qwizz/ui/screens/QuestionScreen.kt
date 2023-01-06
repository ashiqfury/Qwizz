package com.example.qwizz.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.qwizz.R
import com.example.qwizz.ui.theme.QColors
import com.example.qwizz.ui.utils.StatusBarInsetHandler
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@Composable
internal fun QuestionScreen(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = QColors.LightWhite,
        topBar = {
            StatusBarInsetHandler {
                QuestionScreenTopBar(navController)
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QuestionSection()
            BannerImage()
            ChoicesSection(navController)
        }
    }
}

@Composable
private fun QuestionScreenTopBar(navController: NavController) {
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
        },
        actions = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1 / 1f)
                    .background(QColors.LightWhite, CircleShape)
                    .clip(CircleShape)
                    .border(3.dp, QColors.ProgressBarBG, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "4:09",
                    color = QColors.TextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Composable
private fun QuestionSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 30.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            text = "Question 4",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = QColors.TextSecondary,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "What's the diameter of a basketball hoop in inches?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 32.sp,
            color = QColors.TextPrimary,
        )
    }
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
private fun ChoicesSection(
    navController: NavController
) {
    val isFlow = true

    if (isFlow) {
        FlowRow(
            modifier = Modifier.padding(20.dp)
        ) {
            choices.forEach { choice ->
                ChoiceButton(choice, isFlow, navController)
            }
        }
    } else {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            choices.forEach { choice ->
                ChoiceButton(choice, isFlow, navController)
            }
        }
    }

}

@Composable
private fun ChoiceButton(
    choice: String,
    isFlow: Boolean,
    navController: NavController
) {
    val fraction = if (isFlow) 0.5f else 1f
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(fraction = fraction)
            .padding(5.dp),
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(backgroundColor = QColors.LightWhite),
        shape = CircleShape,
        border = BorderStroke(1.dp, QColors.ProgressBarBG),
        contentPadding = PaddingValues(6.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
                .background(QColors.ProgressBarBG.copy(0.5f), CircleShape)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                fontSize = 13.sp,
                color = QColors.TextPrimary,
                text = (choices.indexOf(choice) + 1).toString()
            )
        }
        Text(
            modifier = Modifier.padding(10.dp),
            text = choice,
            fontSize = 13.sp,
            color = QColors.TextPrimary
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
private fun QuestionScreenPreview() {
    val navController = rememberAnimatedNavController()
    QuestionScreen(navController)
}

private val choices = listOf(
    "18 inches",
    "16 inches",
    "14 inches",
    "20 inches"
)