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

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.DuckeeButton
import xyz.duckee.android.core.designsystem.R
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
internal fun CollectionBalance(
    modifier: Modifier = Modifier,
    balance: Double,
    estimateBalance: Double,
    onAddButtonClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color(0xFF2A333A), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_duck_balance),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .border(width = 1.dp, color = Color.White, shape = CircleShape),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = balance.toString(),
                style = DuckeeTheme.typography.title1,
                color = Color.White,
            )
            Text(
                text = "≈ \$ $estimateBalance",
                style = DuckeeTheme.typography.paragraph6,
                color = Color.White,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        DuckeeButton(
            label = "Add",
            labelStyle = DuckeeTheme.typography.paragraph5,
            labelColor = Color(0xFFFBFBFB),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
            backgroundColor = Color(0xFF2A333A),
            shape = RoundedCornerShape(8.dp),
            onClick = onAddButtonClick,
        )
    }
}
