package xyz.duckee.android.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

private val darkColorPalette = lightColors(
    primary = Color.White,
    secondary = Color(0xff2A333A),
    background = Color(0xff08090A),
)

@Composable
fun DuckeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    // TODO: Light Mode
    val colors = if (darkTheme) {
        darkColorPalette
    } else {
        darkColorPalette
    }

    MaterialTheme(
        colors = colors,
    ) {
        CompositionLocalProvider(
            LocalDuckeeTypography provides DuckeeTypography,
            content = content,
        )
    }
}

object DuckeeTheme {
    val typography: DuckeeTypography
        @ReadOnlyComposable
        @Composable
        get() = LocalDuckeeTypography.current
}
