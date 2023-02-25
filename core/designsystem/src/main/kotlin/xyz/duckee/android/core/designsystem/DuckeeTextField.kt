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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
fun DuckeeTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeHolder: String,
    onlyNumber: Boolean = false,
    onValueChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit = {},
) {
    var focusState by remember { mutableStateOf<FocusState?>(null) }
    var isFocused by remember { mutableStateOf(false) }
    val isKeyboardVisible by rememberUpdatedState(WindowInsets.ime.getBottom(LocalDensity.current) > 0)

    LaunchedEffect(Unit) {
        snapshotFlow { isKeyboardVisible }
            .distinctUntilChanged()
            .collect {
                isFocused = it && focusState?.isFocused == true
                onFocusChanged(isFocused)
            }
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        cursorBrush = SolidColor(Color.White),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (onlyNumber) KeyboardType.Number else KeyboardType.Text,
        ),
        textStyle = DuckeeTheme.typography.paragraph3.copy(
            fontWeight = FontWeight.Light,
            color = Color(0xFFFBFBFB),
        ),
        modifier = modifier
            .onFocusChanged {
                focusState = it
                isFocused = it.isFocused
                onFocusChanged(isFocused)
            }
            .let { m ->
                if (isFocused) {
                    m.border(width = 1.dp, color = Color(0xFF45A2FF), shape = RoundedCornerShape(16.dp))
                } else {
                    m.border(width = 1.dp, color = Color(0xFF49565E), shape = RoundedCornerShape(16.dp))
                }
            }
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) { nativeTextField ->
        if (value.isBlank()) {
            Text(
                text = placeHolder,
                style = DuckeeTheme.typography.paragraph3,
                color = Color(0xFF7C8992),
            )
        }

        nativeTextField()
    }
}

@Preview(name = "Duckee text field component", showBackground = true, backgroundColor = 0xFF000000)
@Composable
internal fun DuckeeTextFieldPreview() {
    DuckeeTheme {
        DuckeeTextField(
            value = "",
            onValueChanged = {},
            placeHolder = "Input anything...",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        )
    }
}
