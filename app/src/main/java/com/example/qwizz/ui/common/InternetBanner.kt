package com.example.qwizz.ui.common

import android.os.Build
import android.util.Log
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.qwizz.R
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

    val liveState: ConnectionState by observeConnectivityState()
    val isConnected = liveState == ConnectionState.Available

    val bannerBgColor = if (isConnected) QColors.BabyGreen else QColors.Tomato
    val bannerText = if (isConnected) stringResource(R.string.internet_connected) else stringResource(R.string.connection_failed)
    var translate by remember { mutableStateOf(100f) }
    val animateTranslate by animateFloatAsState(targetValue = translate)

    val lifecycle: LifecycleOwner = LocalLifecycleOwner.current
    val isInitialRender: Boolean = lifecycle.lifecycle.currentState == Lifecycle.State.RESUMED

    Log.d("FURY", ">>>> isInitialRender -> $isInitialRender")
    Log.d("FURY", ">>>> my state -> ${lifecycle.lifecycle.currentState}")

    LaunchedEffect(liveState) {
        translate = 0f
        delay(timeMillis = 3000)
        translate = 150f

    }

    val brush: Brush = Brush.verticalGradient(listOf(Color.Transparent, bannerBgColor))

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