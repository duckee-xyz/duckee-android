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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.foundation.clickableSingle
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
fun DuckeeExpandable(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    isExpanded: Boolean = false,
    withoutText: Boolean = false,
    onClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        val arrowRotateValue by animateFloatAsState(targetValue = if (isExpanded) -360f else -180f)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color(0xFF2A333A), shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 20.dp, vertical = 15.dp)
                .clickableSingle(onClick = onClick),
        ) {
            Text(
                text = title,
                style = DuckeeTheme.typography.paragraph4,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFD0D7DD),
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = null,
                tint = Color(0xFF7C8992),
                modifier = Modifier
                    .rotate(90f)
                    .rotate(arrowRotateValue),
            )
        }
        if (!withoutText) {
            AnimatedVisibility(visible = isExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xff2A333A))
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                ) {
                    Text(
                        text = value.trim(),
                        style = DuckeeTheme.typography.paragraph4,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFFBFBFB),
                    )
                }
            }
        }
    }
}

@Preview(name = "Duckee expandable component", showBackground = true, backgroundColor = 0xff000000)
@Composable
internal fun DuckeeExpandablePreview() {
    DuckeeTheme {
        var isExpanded by remember { mutableStateOf(false) }

        DuckeeExpandable(
            title = "Title",
            value = "female Sailor moonassimilated by alien fungus, intricate Three-point lighting ortrait, by Ching Yeh and Greg Rutkowski, detailed cyberpunk in the style of GitS 1995",
            isExpanded = isExpanded,
            onClick = { isExpanded = !isExpanded },
        )
    }
}
