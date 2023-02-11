package com.example.qwizz.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import com.example.qwizz.R
import com.example.qwizz.data.utils.pickNItems
import com.example.qwizz.model.Question
import com.example.qwizz.ui.common.QHorizontalPagerIndicator
import com.example.qwizz.ui.theme.QColors
import com.example.qwizz.ui.utils.StatusBarInsetHandler
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


private const val PAGE_COUNT = 10

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun QuestionScreen(
    navController: NavController,
    questions: List<Question>
) {

    var requiredMultiChoiceQuestions by remember { mutableStateOf(questions) }

    LaunchedEffect(key1 = Unit) {
        val multipleChoiceQuestions = questions.filter { it.type == "multiple" }
        val requiredQuestions = pickNItems(multipleChoiceQuestions, PAGE_COUNT)
        requiredMultiChoiceQuestions = requiredQuestions
    }

    val questionsIndicatorList: SnapshotStateList<Boolean?> = remember { mutableStateListOf(null) }

    LaunchedEffect(Unit) {
        questionsIndicatorList.clear()
        requiredMultiChoiceQuestions.forEach {
            questionsIndicatorList.add(null)
        }
    }

    val pagerState = rememberPagerState(pageCount = PAGE_COUNT)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = QColors.LightWhite,
        topBar = {
            StatusBarInsetHandler {
                QuestionScreenTopBar(navController, pagerState = pagerState, questionsIndicatorList = questionsIndicatorList)
            }
        },
    ) { paddingValues ->

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
                val question = requiredMultiChoiceQuestions[page]
                QuestionSection(pagerState, question)
                BannerImage()
                ChoicesSection(navController, pagerState, question, questionsIndicatorList)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun QuestionScreenTopBar(navController: NavController, pagerState: PagerState, questionsIndicatorList: SnapshotStateList<Boolean?>) {
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
            QHorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier,
                activeColor = QColors.Saffron,
                inactiveColor = QColors.ProgressBarBG,
                indicatorShape = RoundedCornerShape(2.dp),
                choiceList = questionsIndicatorList
            )
//            Text(
//                text = stringResource(R.string.back),
//                modifier = Modifier
//                    .fillMaxWidth(),
//                fontSize = 18.sp,
//                color = QColors.TextPrimary
//            )
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
private fun QuestionSection(pagerState: PagerState, question: Question) {
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
            text = question.question,
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
    pagerState: PagerState,
    question: Question,
    questionsIndicatorList: SnapshotStateList<Boolean?>
) {
    val choices: MutableList<String> = question.incorrect_answers as MutableList<String>
    choices.add((0..2).random(), question.correct_answer)

//    val isFlow = true
    val isFlow = choices.maxOf { it.length } < 8

    val RenderButtons = @Composable {
        choices.forEachIndexed { pageIndex, choice ->
            ChoiceButton(choices, pageIndex, isFlow, navController, pagerState, question.correct_answer, questionsIndicatorList)
        }
    }

    if (isFlow) {
        FlowRow(
            modifier = Modifier.padding(20.dp)
        ) {
            RenderButtons()
        }
    } else {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            RenderButtons()
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ChoiceButton(
    choices: List<String>,
    choiceIndex: Int,
    isFlow: Boolean,
    navController: NavController,
    pagerState: PagerState,
    correctAnswer: String,
    questionsIndicatorList: SnapshotStateList<Boolean?>
) {
    val fraction = if (isFlow) 0.5f else 1f

    val coroutineScope = rememberCoroutineScope()
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(fraction = fraction)
            .padding(5.dp),
        onClick = {

            questionsIndicatorList[pagerState.currentPage] = choices[choiceIndex] == correctAnswer
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
                text = (choiceIndex + 1).toString()
            )
        }
        Text(
            modifier = Modifier.padding(10.dp),
            text = choices[choiceIndex],
            fontSize = 13.sp,
            color = QColors.TextPrimary
        )
    }
}

//@OptIn(ExperimentalAnimationApi::class)
//@Preview(showBackground = true)
//@Composable
//private fun QuestionScreenPreview() {
//    val navController = rememberAnimatedNavController()
//    QuestionScreen(navController, listOf())
//}
