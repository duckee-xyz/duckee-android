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
package xyz.duckee.android.feature.recipe

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectSideEffect
import xyz.duckee.android.core.designsystem.DuckeeButton
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.feature.recipe.contract.RecipeSideEffect

@Composable
internal fun RecipeGenerateWelcomeRoute(
    viewModel: RecipeGenerateWelcomeViewModel = hiltViewModel(),
    goGenerateScreen: (Boolean) -> Unit,
) {
    viewModel.collectSideEffect {
        if (it is RecipeSideEffect.GoGenerateScreen) {
            goGenerateScreen(it.importMode)
        }
    }

    RecipeGenerateWelcomeScreen(
        onGenerateButtonClick = viewModel::onGenerateButtonClick,
        onImportButtonClick = viewModel::onImportButtonClick,
    )
}

@Composable
internal fun RecipeGenerateWelcomeScreen(
    onGenerateButtonClick: () -> Unit,
    onImportButtonClick: () -> Unit,
) {
    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Let’s Generate\n" +
                    "Your Own \uD83D\uDDBC NFT",
                style = DuckeeTheme.typography.h2,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "You have",
                style = DuckeeTheme.typography.title1,
                fontWeight = FontWeight.Light,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "0 Duck",
                style = DuckeeTheme.typography.title1,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(54.dp))
            DuckeeButton(
                label = "Generate",
                labelColor = Color(0xFF08090A),
                labelStyle = DuckeeTheme.typography.title1,
                backgroundColor = Color.White,
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 14.dp),
                onClick = onGenerateButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .border(width = 1.dp, color = Color(0xFF49565E), shape = RoundedCornerShape(24.dp)),
            )
            Spacer(modifier = Modifier.height(8.dp))
            DuckeeButton(
                label = "Import",
                labelColor = Color(0xFFFBFBFB),
                labelStyle = DuckeeTheme.typography.title1,
                backgroundColor = Color.Transparent,
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 14.dp),
                onClick = onImportButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .border(width = 1.dp, color = Color(0xFF49565E), shape = RoundedCornerShape(24.dp)),
            )
            Spacer(modifier = Modifier.height(80.dp))
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
