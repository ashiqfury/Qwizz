package com.example.qwizz.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
internal fun StatusBarInsetHandler(inset: Dp = 30.dp, content: @Composable (() -> Unit)) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = inset)
    ) {
        content()
    }
}