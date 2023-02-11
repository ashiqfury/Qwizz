package com.example.qwizz.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qwizz.ui.theme.QColors
import com.example.qwizz.ui.utils.QShapes
import com.example.qwizz.ui.utils.StatusBarInsetHandler


@Composable
internal fun ResultScreen(
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        backgroundColor = QColors.LightWhite,
        topBar = {
            StatusBarInsetHandler {

            }
        },
        drawerBackgroundColor = QColors.LightWhite,
        drawerElevation = 8.dp,
//        drawerShape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
        drawerShape = QShapes.DrawerShapes.shape
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 20.dp) // extra space at bottom of the screen
        ) {
//            WelcomeText(scrollState)
//
//            val subjectGridData = getSubjectGridData()
//            SubjectGrid(subjectGridData, navController)
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
        Text(text = "Congratulations", color = QColors.Golden)
    }
}