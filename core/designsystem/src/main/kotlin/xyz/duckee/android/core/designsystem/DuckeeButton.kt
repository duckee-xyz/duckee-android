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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.foundation.clickableSingle
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
fun DuckeeButton(
    modifier: Modifier = Modifier,
    label: String,
    labelStyle: TextStyle = DuckeeTheme.typography.title1,
    labelColor: Color = Color(0xff08090a),
    backgroundColor: Color = Color(0xffffffff),
    shape: Shape = RoundedCornerShape(24.dp),
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
    icon: (@Composable () -> Unit)? = null,
    iconSpacing: Dp = 12.dp,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .clickableSingle(onClick = onClick),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(contentPadding),
        ) {
            icon?.let {
                it()
                Spacer(modifier = Modifier.width(iconSpacing))
            }

            Text(text = label, style = labelStyle, color = labelColor)
        }
    }
}

@Preview(name = "Duckee Button")
@Composable
internal fun DuckeeButtonPreview() {
    DuckeeTheme {
        DuckeeButton(
            label = "This is a button",
            onClick = {},
        )
    }
}
