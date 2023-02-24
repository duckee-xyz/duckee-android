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
package xyz.duckee.android.feature.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.duckee.android.core.designsystem.DuckeeAppBar
import xyz.duckee.android.core.designsystem.DuckeeButton
import xyz.duckee.android.core.designsystem.R
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.designsystem.theme.PromptFont

@Composable
internal fun SignInRoute(
    viewModel: SignInViewModel = hiltViewModel(),
) {
    SignInScreen()
}

@Composable
internal fun SignInScreen() {
    Scaffold {
        DuckeeAppBar()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "\uD83D\uDC24\nQwack! Qwack!\nHello Duckee!\n",
                style = DuckeeTheme.typography.h2,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            DuckeeButton(
                label = "Sign with Google",
                labelStyle = DuckeeTheme.typography.title1.copy(
                    fontFamily = PromptFont,
                ),
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_google_logo),
                        contentDescription = "Google logo",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp),
                    )
                },
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(name = "Sign in screen")
@Composable
internal fun SignInScreenPreview() {
    DuckeeTheme {
        SignInScreen()
    }
}
