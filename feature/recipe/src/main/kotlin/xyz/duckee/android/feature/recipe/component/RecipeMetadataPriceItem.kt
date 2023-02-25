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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.DuckeeTextField
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
internal fun RecipeMetadataPriceItem(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        Text(
            text = "\uD83D\uDCB0 Your Price?",
            style = DuckeeTheme.typography.h6,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(16.dp))
        DuckeeTextField(
            value = value,
            placeHolder = "price",
            onValueChanged = onValueChanged,
            onlyNumber = true,
            keyboardHideWhenEnterKeyClicked = true,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "$",
                style = DuckeeTheme.typography.paragraph3,
                color = if (value.isNotBlank()) Color(0xFFFFFFFF) else Color(0xFF7C8992),
                modifier = Modifier.padding(end = 8.dp),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "≈ 0 Qwack",
            style = DuckeeTheme.typography.paragraph5,
            color = Color(0xFFE9ECEF),
        )
    }
}
