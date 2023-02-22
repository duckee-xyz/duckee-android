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

val PromptFont = FontFamily(
    Font(
        googleFont = GoogleFont("Prompt"),
        fontProvider = googleFontProvider,
    ),
)

val InterFont = FontFamily(
    Font(
        googleFont = GoogleFont("Inter"),
        fontProvider = googleFontProvider,
    ),
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
    val title1: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = PromptFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = (-0.01).sp,
    )
    val paragraph4: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = InterFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp,
    )
}

internal val LocalDuckeeTypography = staticCompositionLocalOf { DuckeeTypography }
