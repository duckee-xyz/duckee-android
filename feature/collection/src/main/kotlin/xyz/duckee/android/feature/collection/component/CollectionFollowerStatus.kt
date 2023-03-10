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
package xyz.duckee.android.feature.collection.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.designsystem.theme.PPObjectSans

@Composable
internal fun CollectionFollowerStatus(
    modifier: Modifier = Modifier,
    recipeCount: Int,
    followerCount: Int,
    followingCount: Int,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .weight(1f),
        ) {
            Text(
                text = recipeCount.toString(),
                style = DuckeeTheme.typography.paragraph3,
                fontWeight = FontWeight.Medium,
                fontFamily = PPObjectSans,
                color = Color.White,
            )
            Text(
                text = "Recipe",
                style = DuckeeTheme.typography.paragraph5,
                fontFamily = PPObjectSans,
                color = Color(0xFFACB7BF),
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = followerCount.toString(),
                style = DuckeeTheme.typography.paragraph3,
                fontWeight = FontWeight.Medium,
                fontFamily = PPObjectSans,
                color = Color.White,
            )
            Text(
                text = "Follower",
                style = DuckeeTheme.typography.paragraph5,
                color = Color(0xFFACB7BF),
                fontFamily = PPObjectSans,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = followingCount.toString(),
                style = DuckeeTheme.typography.paragraph3,
                fontWeight = FontWeight.Medium,
                fontFamily = PPObjectSans,
                color = Color.White,
            )
            Text(
                text = "Following",
                style = DuckeeTheme.typography.paragraph5,
                color = Color(0xFFACB7BF),
                fontFamily = PPObjectSans,
            )
        }
    }
}
