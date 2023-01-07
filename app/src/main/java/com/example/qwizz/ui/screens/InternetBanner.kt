package com.example.qwizz.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.qwizz.model.ConnectionState
import com.example.qwizz.utils.observeConnectivityState
import com.example.qwizz.ui.theme.QColors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.S)
@Composable
internal fun InternetBanner(mainContent: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        mainContent()
        InternetBannerContent()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun BoxScope.InternetBannerContent() {

    val liveState by observeConnectivityState()
    val isConnected = liveState == ConnectionState.Available

    val bannerBgColor = if (isConnected) QColors.BabyGreen else QColors.Tomato
    val bannerText = if (isConnected) "InternetConnected" else "Connection Failed! Try Again"
    var translate by remember { mutableStateOf(100f) }
    val animateTranslate by animateFloatAsState(targetValue = translate)

    LaunchedEffect(liveState) {
        translate = 0f
        delay(3000)
        translate = 100f
    }

    val brush = Brush.verticalGradient(listOf(Color.Transparent, bannerBgColor))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .zIndex(20f)
            .height(50.dp)
            .graphicsLayer {
                translationY = animateTranslate
            }
            .background(brush, alpha = 0.8f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = bannerText,
            fontSize = 18.sp,
            color = QColors.TextPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}