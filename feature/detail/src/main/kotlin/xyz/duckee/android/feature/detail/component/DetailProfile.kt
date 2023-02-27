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
package xyz.duckee.android.feature.detail.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.DuckeeButton
import xyz.duckee.android.core.designsystem.DuckeeNetworkImage
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
internal fun DetailProfile(
    modifier: Modifier = Modifier,
    profileImageUrl: String,
    name: String,
    address: String,
    isFollowed: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        DuckeeNetworkImage(
            model = profileImageUrl,
            contentDescription = "User profile image",
            modifier = Modifier
                .clip(CircleShape)
                .size(54.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = name,
                style = DuckeeTheme.typography.h5,
                color = Color.White,
            )
            Text(
                text = address.run {
                    if (address.length > 10) {
                        address.substring(0, 5) + "..." + address.substring(address.length - 5, address.length)
                    } else {
                        ""
                    }
                },
                style = DuckeeTheme.typography.paragraph5,
                color = Color(0xFFACB7BF),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        DuckeeButton(
            label = if (isFollowed) "Following" else "Follow",
            labelStyle = DuckeeTheme.typography.paragraph4,
            labelColor = if (isFollowed) Color(0xFFFBFBFB) else Color(0xFF08090A),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            backgroundColor = if (isFollowed) Color(0xFF08090A) else Color(0xFFFBFBFB),
            shape = RoundedCornerShape(8.dp),
            onClick = onClick,
            modifier = Modifier.let { m ->
                if (isFollowed) {
                    m.border(width = 1.dp, color = Color(0xFF49565E), shape = RoundedCornerShape(8.dp))
                } else {
                    m
                }
            },
        )
    }
}

@Preview(name = "Detail profile component", showBackground = true, backgroundColor = 0xff08090A)
@Composable
internal fun DetailProfilePreview() {
    DuckeeTheme {
        DetailProfile(
            profileImageUrl = "profileUrl",
            name = "UserName",
            address = "0x28283784748493872723763474849459548478",
            isFollowed = true,
            onClick = {},
        )
    }
}
