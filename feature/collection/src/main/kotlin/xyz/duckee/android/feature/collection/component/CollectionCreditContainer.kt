package xyz.duckee.android.feature.collection.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.designsystem.theme.PPObjectSans

@Composable
internal fun CollectionCreditContainer(
    modifier: Modifier = Modifier,
    title: String,
    icon: @Composable () -> Unit,
    balance: Double,
    iconSpacing: Dp = 12.dp,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .border(width = 1.dp, color = Color(0xFF2A333A), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = DuckeeTheme.typography.paragraph5,
            fontFamily = PPObjectSans,
            color = Color.White,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(iconSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()
            Text(
                text = balance.toString(),
                style = DuckeeTheme.typography.paragraph3,
                fontFamily = PPObjectSans,
                fontWeight = FontWeight.Medium,
                color = Color.White,
            )
        }
    }
}

@Preview(name = "Collection credit container")
@Composable
internal fun CollectionCreditContainerPreview() {
    DuckeeTheme {
        CollectionCreditContainer(
            title = "AI Credit",
            icon = {
                Icon(
                    painter = painterResource(id = xyz.duckee.android.core.designsystem.R.drawable.icon_duck_balance),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            },
            balance = 1234.56,
        )
    }
}
