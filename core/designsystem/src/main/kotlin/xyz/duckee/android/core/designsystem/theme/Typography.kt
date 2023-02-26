/*
 * Copyright 2023 The Duckee Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.duckee.android.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import xyz.duckee.android.core.designsystem.R

private val googleFontProvider: GoogleFont.Provider =
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs,
    )

val supportWeight = listOf(
    FontWeight.W100,
    FontWeight.W200,
    FontWeight.W300,
    FontWeight.W400,
    FontWeight.W500,
    FontWeight.W600,
    FontWeight.W700,
    FontWeight.W800,
    FontWeight.W900,
)

val PromptFont = FontFamily(
    supportWeight.map {
        Font(
            googleFont = GoogleFont("Prompt"),
            fontProvider = googleFontProvider,
            weight = it,
        )
    },
)

val InterFont = FontFamily(
    supportWeight.map {
        Font(
            googleFont = GoogleFont("Inter"),
            fontProvider = googleFontProvider,
            weight = it,
        )
    },
)

@Immutable
object DuckeeTypography {
    val h1: TextStyle = TextStyle(
        fontSize = 36.sp,
        fontFamily = PromptFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 44.sp,
        letterSpacing = (-0.01).sp,
    )
    val h2: TextStyle = TextStyle(
        fontSize = 32.sp,
        fontFamily = PromptFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 40.sp,
        letterSpacing = (-0.01).sp,
    )
    val h4: TextStyle = TextStyle(
        fontSize = 24.sp,
        fontFamily = PromptFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 30.sp,
        letterSpacing = (-0.01).sp,
    )
    val h5: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontFamily = PromptFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 30.sp,
        letterSpacing = (-0.01).sp,
    )
    val h6: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = PromptFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = (-0.01).sp,
    )
    val title1: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = PromptFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = (-0.01).sp,
    )
    val paragraph3: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = InterFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
    )
    val paragraph4: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = InterFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
    )
    val paragraph5: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontFamily = InterFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp,
    )
    val paragraph6: TextStyle = TextStyle(
        fontSize = 10.sp,
        fontFamily = InterFont,
        fontWeight = FontWeight.Light,
        lineHeight = 12.sp,
    )
}

internal val LocalDuckeeTypography = staticCompositionLocalOf { DuckeeTypography }
