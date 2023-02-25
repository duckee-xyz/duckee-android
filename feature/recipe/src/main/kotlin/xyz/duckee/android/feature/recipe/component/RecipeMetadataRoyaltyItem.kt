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
package xyz.duckee.android.feature.recipe.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
internal fun RecipeMetadataRoyaltyItem(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChanged: (Int) -> Unit,
) {
    var cachedValue by remember { mutableStateOf(0f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        Text(
            text = "\uD83E\uDD11 Set your Royalty",
            style = DuckeeTheme.typography.h6,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "$value%",
            style = DuckeeTheme.typography.paragraph3,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF45A2FF),
        )
        Slider(
            value = cachedValue,
            onValueChange = {
                cachedValue = it
                onValueChanged(it.toInt())
            },
            valueRange = 0f..50f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFFBFBFB),
                inactiveTrackColor = Color(0xFF2A333A),
                activeTrackColor = Color(0xFF45A2FF),
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "You can set max. 50% of your design",
            style = DuckeeTheme.typography.paragraph5,
            fontWeight = FontWeight.Light,
            color = Color(0xFFACB7BF),
        )
    }
}
