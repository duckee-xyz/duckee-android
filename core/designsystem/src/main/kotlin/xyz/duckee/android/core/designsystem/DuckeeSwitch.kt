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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
fun DuckeeSwitch(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    width: Dp = 26.dp,
    height: Dp = 16.dp,
    gapBetweenThumbAndTrackEdge: Dp = 2.dp,
    onCheckChanged: (Boolean) -> Unit,
) {
    val haptic = LocalHapticFeedback.current
    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

    val updatedChecked by rememberUpdatedState(isChecked)

    val animatePosition = animateFloatAsState(
        targetValue = if (updatedChecked) {
            with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }
        } else {
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }
        },
    )

    val selectedColor = Color(0xFF25D415)
    val unselectedColor = Color(0xFF49565E)

    Canvas(
        modifier = modifier
            .size(width = width, height = height)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onCheckChanged(!updatedChecked)
                    },
                )
            },
    ) {
        // Track
        drawRoundRect(
            color = if (updatedChecked) selectedColor else unselectedColor,
            cornerRadius = CornerRadius(x = 12.dp.toPx(), y = 12.dp.toPx()),
            style = Fill,
        )

        // Thumb
        drawCircle(
            color = Color.White,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2,
            ),
        )
    }
}

@Preview(name = "Duckee switch component")
@Composable
internal fun DuckeeSwitchPreview() {
    DuckeeTheme {
        Box(
            modifier = Modifier.padding(24.dp),
        ) {
            val (isChecked, onChecked) = remember { mutableStateOf(false) }
            DuckeeSwitch(
                isChecked = isChecked,
                onCheckChanged = onChecked,
            )
        }
    }
}
