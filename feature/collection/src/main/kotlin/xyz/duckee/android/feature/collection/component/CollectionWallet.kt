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

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.DuckeeButton
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
internal fun CollectionWallet(
    modifier: Modifier = Modifier,
    profileName: String,
    address: String,
    onLinkButtonClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
    ) {
        Column(
            verticalArrangement = Arrangement
                .spacedBy(4.dp),
        ) {
            Text(
                text = profileName,
                style = DuckeeTheme.typography.title1,
                fontWeight = FontWeight.Medium,
                color = Color.White,
            )
            Text(
                text = address,
                style = DuckeeTheme.typography.paragraph5,
                color = Color(0xFFACB7BF),
                fontWeight = FontWeight.W300,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        DuckeeButton(
            label = "Bring Your Own Wallet",
            labelStyle = DuckeeTheme.typography.paragraph5.copy(
                fontWeight = FontWeight.Medium,
            ),
            iconSpacing = 6.dp,
            labelColor = Color.White,
            backgroundColor = Color(0xFF244728),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 7.dp),
            icon = {
                Icon(
                    painter = painterResource(id = xyz.duckee.android.core.designsystem.R.drawable.icon_flow_logo),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp),
                )
            },
            onClick = onLinkButtonClick,
            modifier = Modifier.border(
                width = 1.dp,
                color = Color(0xFF33633E),
                shape = RoundedCornerShape(8.dp),
            ),
        )
    }
}
