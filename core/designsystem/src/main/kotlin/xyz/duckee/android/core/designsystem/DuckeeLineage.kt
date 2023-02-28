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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
fun DuckeeLineage(
    modifier: Modifier = Modifier,
    parentImageUrl: String,
    childImageUrl: String,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(23.5.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f),
        ) {
            DuckeeNetworkImage(
                model = parentImageUrl,
                contentDescription = "Parent image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Text(
                text = "Parents Recipe",
                style = DuckeeTheme.typography.paragraph4,
                fontWeight = FontWeight.Light,
                color = Color(0xFFFBFBFB),
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.icon_right_arrow_with_circle),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(12.dp),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f),
        ) {
            DuckeeNetworkImage(
                model = childImageUrl,
                contentDescription = "Child image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Text(
                text = "Child Recipe",
                style = DuckeeTheme.typography.paragraph4,
                fontWeight = FontWeight.Light,
                color = Color(0xFFFBFBFB),
            )
        }
    }
}

@Preview(name = "Lineage component", showBackground = true, backgroundColor = 0xFF08090A)
@Composable
internal fun DuckeeLineagePreview() {
    DuckeeTheme {
        DuckeeLineage(
            parentImageUrl = "parentImageUrl",
            childImageUrl = "childImageUrl",
        )
    }
}
