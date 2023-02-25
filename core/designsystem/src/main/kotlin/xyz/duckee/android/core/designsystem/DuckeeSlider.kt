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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
fun DuckeeSlider(
    modifier: Modifier = Modifier,
    steps: ImmutableList<String>,
    onValueChanged: (Int) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        val (selectedIndex, onSelectedIndex) = remember { mutableStateOf(0) }

        Text(
            text = steps[selectedIndex],
            style = DuckeeTheme.typography.paragraph3,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF45A2FF),
        )
        Slider(
            value = selectedIndex.toFloat(),
            onValueChange = {
                onSelectedIndex(it.toInt())
                onValueChanged(it.toInt())
            },
            valueRange = 0.toFloat()..steps.size.toFloat() - 1,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFFBFBFB),
                inactiveTrackColor = Color(0xFF2A333A),
                activeTrackColor = Color(0xFF45A2FF),
            ),
        )
    }
}
