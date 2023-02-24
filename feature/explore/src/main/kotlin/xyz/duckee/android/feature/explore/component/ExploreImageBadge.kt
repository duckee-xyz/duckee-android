package xyz.duckee.android.feature.explore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
internal fun ExploreImageBadge(
    modifier: Modifier = Modifier,
    label: String,
    backgroundColor: Color = Color(0xffBBFFC2).copy(alpha = 0.9f),
    borderColor: Color = Color(0xff9DFFBE),
    icon: (@Composable () -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        icon?.let {
            it()
        }
        Text(
            text = label,
            style = DuckeeTheme.typography.title1,
            color = Color.Black,
        )
    }
}

@Preview(name = "Explore image badge component", showBackground = true)
@Composable
internal fun ExploreImageBadgePreview() {
    DuckeeTheme {
        ExploreImageBadge(
            label = "Open Source",
        )
    }
}
