package com.example.qwizz.utils

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.qwizz.R


@OptIn(ExperimentalTextApi::class)
private val googleFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

@OptIn(ExperimentalTextApi::class)
internal fun getGoogleFont(fontName: String): FontFamily {
    return FontFamily(
        fonts = listOf(
            Font(
                googleFont = GoogleFont(fontName),
                fontProvider = googleFontProvider
            )
        )
    )
}