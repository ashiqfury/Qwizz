package com.example.qwizz.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.qwizz.R
import com.example.qwizz.model.SubjectCard
import com.example.qwizz.ui.navigation.QScreens
import com.example.qwizz.ui.theme.*
import com.example.qwizz.ui.utils.StatusBarInsetHandler
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch

@Composable
internal fun HomeScreen(
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        backgroundColor = QColors.LightWhite,
        topBar = {
            StatusBarInsetHandler {
                HomeScreenTopBar(scaffoldState)
            }
        },
        drawerContent = {
            StatusBarInsetHandler {
                DrawerContent()
            }
        },
        drawerBackgroundColor = QColors.VeryLightWhite,
        drawerElevation = 8.dp,
        drawerShape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 20.dp) // extra space at bottom of the screen
        ) {
            WelcomeText()

            val subjectGridData = getSubjectGridData()
            SubjectGrid(subjectGridData, navController)
        }
    }
}

@Composable
private fun HomeScreenTopBar(
    scaffoldState: ScaffoldState
) {
    val elevation = 8.dp
    val borderColor = QColors.ProgressBarBG
    val shape = CircleShape

    TopAppBar(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
        backgroundColor = QColors.LightWhite,
        navigationIcon = {
            Column(
                modifier = Modifier
                    .size(48.dp)
                    .background(QColors.VeryLightWhite, shape = shape)
                    .clip(shape)
                    .border(width = 1.dp, color = borderColor, shape = shape)
                    .padding(3.dp),
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape),
                    painter = painterResource(id = R.drawable.avatar),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Profile_Avatar",
                )
            }
        },
        actions = {
            val coroutineScope = rememberCoroutineScope()
            Card(
                modifier = Modifier
                    .size(48.dp)
                    .background(QColors.VeryLightWhite, shape = shape)
                    .clip(shape)
                    .shadow(elevation = elevation)
                    .border(width = 1.dp, color = borderColor, shape = shape),
            ) {
                IconButton(
                    modifier = Modifier.fillMaxSize(0.9f),
                    onClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Hamburger_Menu",
                        tint = QColors.TextPrimary
                    )
                }
            }
        },
        elevation = 0.dp,
        title = {
            /*Text(
                text = "Qwizz",
                modifier = Modifier
                    .fillMaxWidth(),
//                    .padding(horizontal = 20.dp, vertical = 40.dp),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 26.sp,
                color = QColors.TextPrimary
            )*/
        }
    )
}

@Composable
private fun WelcomeText() {
    Text(
        text = stringResource(R.string.welcome_text),
        modifier = Modifier
            .fillMaxWidth(0.76f)
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 40.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 26.sp,
        color = QColors.TextPrimary
    )
}

@Composable
private fun SubjectGrid(
    subjectData: List<SubjectCard>,
    navController: NavController
) {
    FlowRow(
        modifier = Modifier.padding(5.dp)
    ) {
        subjectData.forEach { subject ->
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(5.dp)
                    .shadow(
                        elevation = 4.dp,
                        clip = true,
                        shape = RoundedCornerShape(12.dp),
                        ambientColor = subject.progressColor,
                        spotColor = subject.progressColor
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(QColors.VeryLightWhite)
                    .clickable {
                        navController.navigate(route = QScreens.Difficulty.route) {
                            launchSingleTop = true
                        }
                    },
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(vertical = 5.dp)
                            .aspectRatio(1f / 1f),
                        painter = painterResource(subject.illustration),
                        contentDescription = subject.title,
                    )

                    Text(
                        text = subject.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 26.sp,
                        color = QColors.TextPrimary
                    )

                    val progress = subject.progressValue.toFloat() / 100
                    LinearProgressIndicator(
                        modifier = Modifier
                            .padding(vertical = 15.dp, horizontal = 30.dp)
                            .clip(CircleShape)
                            .height(6.dp),
                        progress = progress,
                        color = subject.progressColor,
                        backgroundColor = QColors.ProgressBarBG
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawerContent() {
    val textModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp)
        .background(QColors.LightWhite, shape = RoundedCornerShape(12.dp))
        .border(0.5f.dp, QColors.Tomato, RoundedCornerShape(12.dp))
        .padding(vertical = 10.dp, horizontal = 20.dp)
        .clickable { }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(QColors.VeryLightWhite)
            .padding(10.dp)
    ) {
        Text(text = "Home", modifier = textModifier)
        Text(text = "Home", modifier = textModifier)
        Text(text = "Home", modifier = textModifier)
        Text(text = "Home", modifier = textModifier)
        Text(text = "Home", modifier = textModifier)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
private fun HomePagePreview() {
    val navController = rememberAnimatedNavController()
    HomeScreen(navController)
}

private fun getSubjectGridData(): List<SubjectCard> {
    return listOf(
        SubjectCard(
            title = "Art & Literature",
            illustration = R.drawable.subject_arts,
            progressValue = 100,
            progressColor = QColors.Saffron
        ),
        SubjectCard(
            title = "Sport",
            illustration = R.drawable.subject_sports,
            progressValue = 50,
            progressColor = QColors.Purple
        ),
        SubjectCard(
            title = "Mathematics",
            illustration = R.drawable.subject_mathematics,
            progressValue = 30,
            progressColor = QColors.InkBlue
        ),
        SubjectCard(
            title = "Science",
            illustration = R.drawable.subject_science,
            progressValue = 80,
            progressColor = QColors.Tomato
        ),
        SubjectCard(
            title = "History",
            illustration = R.drawable.subject_history,
            progressValue = 10,
            progressColor = QColors.BabyPink
        ),
        SubjectCard(
            title = "Music",
            illustration = R.drawable.subject_music,
            progressValue = 20,
            progressColor = QColors.BabyGreen
        )
    )
}