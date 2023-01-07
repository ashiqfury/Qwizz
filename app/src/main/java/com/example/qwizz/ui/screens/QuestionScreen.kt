package com.example.qwizz.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import com.example.qwizz.R
import com.example.qwizz.ui.theme.QColors
import com.example.qwizz.ui.utils.StatusBarInsetHandler
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
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

        val pagerState = rememberPagerState(pageCount = 10)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier,
                activeColor = QColors.Saffron,
                inactiveColor = QColors.ProgressBarBG,
                indicatorShape = RoundedCornerShape(2.dp)
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier,
            dragEnabled = false
        ) { page ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.75f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.4f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuestionSection(pagerState)
                BannerImage()
                ChoicesSection(navController, pagerState)
            }
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

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun QuestionSection(pagerState: PagerState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 30.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            text = "Question ${pagerState.currentPage + 1}",
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

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ChoicesSection(
    navController: NavController,
    pagerState: PagerState
) {
    val isFlow = true

    if (isFlow) {
        FlowRow(
            modifier = Modifier.padding(20.dp)
        ) {
            choices.forEach { choice ->
                ChoiceButton(choice, isFlow, navController, pagerState)
            }
        }
    } else {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            choices.forEach { choice ->
                ChoiceButton(choice, isFlow, navController, pagerState)
            }
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ChoiceButton(
    choice: String,
    isFlow: Boolean,
    navController: NavController,
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()
    val fraction = if (isFlow) 0.5f else 1f
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(fraction = fraction)
            .padding(5.dp),
        onClick = {
            coroutineScope.launch {
                if (pagerState.currentPage + 1 < 10) {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        },
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