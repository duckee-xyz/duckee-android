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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.DuckeeNetworkImage
import xyz.duckee.android.core.designsystem.foundation.clickableSingle
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
internal fun RecipeModelType(
    modifier: Modifier = Modifier,
    imageUrl: String,
    modelLabel: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        DuckeeNetworkImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(16.dp))
                .let { m ->
                    if (isSelected) {
                        m.alpha(1.0f)
                    } else {
                        m.alpha(0.6f)
                    }
                }
                .clickableSingle(onClick = onClick),
        )
        Text(
            text = modelLabel,
            style = DuckeeTheme.typography.paragraph5,
            color = if (isSelected) Color.White else Color(0xFF7C8992),
        )
    }
}

@Preview(name = "Recipe model type component", showBackground = true, backgroundColor = 0xFF000000)
@Composable
internal fun RecipeModelTypePreview() {
    DuckeeTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            RecipeModelType(
                imageUrl = "imageUrl",
                modelLabel = "Dall-E",
                isSelected = false,
                onClick = {},
            )
            RecipeModelType(
                imageUrl = "imageUrl",
                modelLabel = "Dall-E",
                isSelected = true,
                onClick = {},
            )
        }
    }
}
