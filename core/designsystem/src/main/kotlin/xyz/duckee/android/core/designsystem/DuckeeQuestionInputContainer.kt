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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
fun DuckeeQuestionInputContainer(
    modifier: Modifier = Modifier,
    categoryLabel: String,
    questionLabel: String,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = categoryLabel,
            style = DuckeeTheme.typography.paragraph5,
            color = Color(0xFF7C8992),
            modifier = Modifier.padding(horizontal = 24.dp),
        )
        Text(
            text = questionLabel,
            style = DuckeeTheme.typography.h5,
            color = Color(0xFFFFFFFF),
            modifier = Modifier.padding(horizontal = 24.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }
}

@Preview(name = "Duckee question input container", showBackground = true, backgroundColor = 0xff000000)
@Composable
internal fun DuckeeQuestionInputContainerPreview() {
    DuckeeTheme {
        DuckeeQuestionInputContainer(
            categoryLabel = "Category 1",
            questionLabel = "What is this?",
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp),
        ) {
        }
    }
}
