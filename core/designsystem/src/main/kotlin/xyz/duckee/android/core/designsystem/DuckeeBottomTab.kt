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
package xyz.duckee.android.core.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.foundation.clickableSingle
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
fun DuckeeBottomTab(
    modifier: Modifier = Modifier,
    currentRoute: String,
    onClick: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(21.5.dp),
        modifier = modifier
            .clip(RoundedCornerShape(40.dp))
            .background(Color(0xff2A333A))
            .padding(horizontal = 24.dp, vertical = 12.dp),
    ) {
        DuckeeBottomTabIcon(
            iconResource = R.drawable.icon_explore,
            isActive = currentRoute.startsWith("explore"),
            onClick = { onClick("explore") },
        )
        Box {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF08090A)),
            )
            DuckeeBottomTabIcon(
                iconResource = R.drawable.icon_plus_with_border,
                isActive = currentRoute.startsWith("recipe"),
                iconSize = 20.dp,
                onClick = { onClick("recipe") },
            )
        }
        DuckeeBottomTabIcon(
            iconResource = R.drawable.icon_my_profile,
            isActive = currentRoute.startsWith("collection"),
            onClick = { onClick("collection") },
        )
    }
}

@Composable
private fun DuckeeBottomTabIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconResource: Int,
    isActive: Boolean,
    iconSize: Dp = 32.dp,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickableSingle(onClick = onClick)
            .size(48.dp),
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
            tint = if (isActive) Color.White else Color(0xffACB7BF),
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
        )
    }
}

@Preview(name = "Bottom tab component")
@Composable
internal fun DuckeeBottomTabPreview() {
    DuckeeTheme {
        DuckeeBottomTab(
            currentRoute = "",
            onClick = {},
        )
    }
}
