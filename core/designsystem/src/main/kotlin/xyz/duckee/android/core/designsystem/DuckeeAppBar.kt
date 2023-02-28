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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.foundation.clickableSingle
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.ui.LocalNavigationPopStack

@Composable
fun DuckeeAppBar(
    modifier: Modifier = Modifier,
    isBackButtonDimEnabled: Boolean = false,
) {
    val backHandler = LocalNavigationPopStack.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Spacer(modifier = Modifier.width(6.dp))
        Icon(
            painter = painterResource(id = R.drawable.icon_back),
            contentDescription = "Back button",
            tint = Color(0xFFFBFBFB),
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickableSingle(onClick = backHandler)
                .let { m ->
                    if (isBackButtonDimEnabled) {
                        m.background(Color(0xFF08090A).copy(alpha = 0.7f))
                    } else {
                        m
                    }
                },
        )
    }
}

@Preview(name = "Duckee app bar component")
@Composable
internal fun DuckeeAppBarPreview() {
    DuckeeTheme {
        DuckeeAppBar(
            isBackButtonDimEnabled = true,
        )
    }
}
