package com.example.qwizz.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.qwizz.utils.QFonts
import com.example.qwizz.utils.getGoogleFont

// Set of Material typography styles to start with
val TypographyNative = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)


val fontFamily = getGoogleFont(fontName = QFonts.POPPINS.fontName)

val Typography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = 57.sp,
        letterSpacing = (-0.25).sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = 45.sp,
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = 36.sp,
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = 32.sp,
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = 28.sp,
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = 17.sp,
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.W700,
        fontFamily = fontFamily,
        fontSize = 20.sp,
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.W700,
        fontFamily = fontFamily,
        fontSize = 18.sp,
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = fontFamily,
        fontSize = 16.sp,
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = 15.sp,
    ),
    button = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = 14.sp,
    ),
    caption = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = 12.sp,
    ),
    overline = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 8.sp,
    ),
)