package com.example.qwizz.model

import androidx.compose.ui.graphics.Color

data class SubjectCard(
    val title: String,
    val illustration: Int,
    val progressValue: Int,
    val progressColor: Color
)