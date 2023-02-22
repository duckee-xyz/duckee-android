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

val PromptFont = getGoogleFontFamily(
    name = "Prompt",
    weights = listOf(
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
)

@Immutable
object DuckeeTypography {
    val h1: TextStyle = TextStyle(
        fontSize = 36.sp,
        fontFamily = PromptFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 44.sp,
        letterSpacing = (-0.01).sp
    )
    val h2: TextStyle = TextStyle(
        fontSize = 32.sp,
        fontFamily = PromptFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 40.sp,
        letterSpacing = (-0.01).sp
    )
    val paragraph3: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = PromptFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = (-0.01).sp
    )
    val paragraph4: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = PromptFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp,
        letterSpacing = (-0.01).sp
    )
}

private fun getGoogleFontFamily(
    name: String,
    provider: GoogleFont.Provider = googleFontProvider,
    weights: List<FontWeight>,
): FontFamily {
    return FontFamily(
        weights.map {
            Font(GoogleFont(name), provider, it)
        }
    )
}

private val googleFontProvider: GoogleFont.Provider by lazy {
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs,
    )
}

internal val LocalDuckeeTypography = staticCompositionLocalOf { DuckeeTypography }
