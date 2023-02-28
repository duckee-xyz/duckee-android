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

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
fun DuckeeSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    placeHolder: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(width = 1.dp, color = Color(0xFF7C8992), shape = RoundedCornerShape(24.dp)),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_search),
            contentDescription = null,
            tint = Color(0xff7C8992),
            modifier = Modifier
                .padding(start = 12.dp, top = 10.dp, bottom = 10.dp)
                .size(28.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChanged,
            textStyle = DuckeeTheme.typography.paragraph3.copy(
                color = Color.White,
                fontWeight = FontWeight.Normal,
            ),
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier.weight(1f),
        ) { nativeTextField ->
            if (value.isEmpty()) {
                Text(
                    text = placeHolder,
                    style = DuckeeTheme.typography.paragraph3.copy(
                        fontWeight = FontWeight.Normal,
                    ),
                    color = Color(0xFF7C8992),
                )
            }

            nativeTextField()
        }
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Preview(name = "Search bar", showBackground = true, backgroundColor = 0xFF000000)
@Composable
internal fun DuckeeSearchBarPreview() {
    DuckeeTheme {
        DuckeeSearchBar(
            value = "",
            onValueChanged = {},
            placeHolder = "Search anything...",
        )
    }
}
