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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.designsystem.theme.PromptFont
import xyz.duckee.android.core.ui.RandomImageUrlGenerator

@Composable
internal fun DuckeeHorizontalNftCarousel(
    modifier: Modifier = Modifier,
    list: ImmutableList<String>,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        items(list) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                DuckeeNetworkImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .size(144.dp)
                        .clip(RoundedCornerShape(16.dp)),
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val profileImage = rememberSaveable { RandomImageUrlGenerator.getRandomImageUrl() }
                    DuckeeNetworkImage(
                        model = profileImage,
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape),
                    )
                    Text(
                        text = "ShibaSaki",
                        style = DuckeeTheme.typography.paragraph4,
                        fontFamily = PromptFont,
                        color = Color(0xFFFBFBFB),
                    )
                }
            }
        }
    }
}

@Preview(name = "Detail derived nft carousel component", showBackground = true, backgroundColor = 0xFF08090A)
@Composable
internal fun DuckeeHorizontalNftCarouselScreen() {
    DuckeeTheme {
        DuckeeHorizontalNftCarousel(
            list = persistentListOf(
                "TEST",
                "TEST",
                "TEST",
            ),
        )
    }
}
