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

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.DuckeeNetworkImage

@Composable
internal fun CollectionProfile(
    modifier: Modifier = Modifier,
    profileUrl: String,
    recipeCount: Int,
    followerCount: Int,
    followingCount: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
    ) {
        DuckeeNetworkImage(
            model = profileUrl,
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.weight(1f))
        CollectionFollowerStatus(
            recipeCount = recipeCount,
            followerCount = followerCount,
            followingCount = followingCount,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
