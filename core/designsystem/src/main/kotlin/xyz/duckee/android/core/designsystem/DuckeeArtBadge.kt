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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.designsystem.theme.PPObjectSans

@Composable
fun DuckeeArtBadge(
    modifier: Modifier = Modifier,
    label: String,
    backgroundColor: Color = Color(0xffBBFFC2).copy(alpha = 0.9f),
    borderColor: Color = Color(0xff9DFFBE),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    icon: (@Composable () -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(20.dp))
            .padding(contentPadding),
    ) {
        icon?.let {
            it()
        }
        Text(
            text = label,
            style = DuckeeTheme.typography.title1,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
        )
    }
}

@Composable
fun DuckeeArtBadgeSmall(
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
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        icon?.let {
            it()
        }
        Text(
            text = label,
            style = DuckeeTheme.typography.paragraph4,
            fontFamily = PPObjectSans,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
        )
    }
}
