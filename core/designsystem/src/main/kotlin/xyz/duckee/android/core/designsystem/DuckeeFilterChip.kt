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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.foundation.clickableSingle
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.designsystem.theme.PPObjectSans

@Composable
fun DuckeeFilterChip(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    isActive: Boolean = false,
) {
    Text(
        text = label,
        color = if (isActive) {
            Color(0xFF08090A)
        } else {
            Color(0xFFFBFBFB)
        },
        style = DuckeeTheme.typography.paragraph4.copy(
            fontFamily = PPObjectSans,
        ),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickableSingle(onClick = onClick)
            .let { m ->
                if (isActive) {
                    m.background(Color(0xfffbfbfb))
                } else {
                    m
                }
            }
            .border(width = 1.dp, color = Color(0xFFD0D7DD), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
    )
}

@Preview(name = "Duckee filter chip component", showBackground = true, backgroundColor = 0xff000000)
@Composable
internal fun DuckeeFilterChipPreview() {
    DuckeeTheme {
        DuckeeFilterChip(
            label = "TEST",
            onClick = {},
        )
    }
}

@Preview(name = "Duckee filter chip component - Active", showBackground = true, backgroundColor = 0xff000000)
@Composable
internal fun DuckeeFilterChipActiveStatusPreview() {
    DuckeeTheme {
        DuckeeFilterChip(
            label = "TEST",
            isActive = true,
            onClick = {},
        )
    }
}
