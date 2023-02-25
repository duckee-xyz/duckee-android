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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectSideEffect
import xyz.duckee.android.core.designsystem.DuckeeAppBar
import xyz.duckee.android.core.designsystem.DuckeeButton
import xyz.duckee.android.core.designsystem.DuckeeNetworkImage
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.designsystem.theme.PromptFont
import xyz.duckee.android.core.ui.LocalNavigationPopStack
import xyz.duckee.android.feature.recipe.contract.RecipeResultState
import xyz.duckee.android.feature.recipe.contract.RecipeSideEffect

@Composable
internal fun RecipeResultRoute(
    viewModel: RecipeResultViewModel = hiltViewModel(),
    goRecipeMetadataScreen: (String) -> Unit,
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val backHandler = LocalNavigationPopStack.current

    viewModel.collectSideEffect {
        if (it is RecipeSideEffect.GoRecipeMetadataScreen) {
            goRecipeMetadataScreen(it.resultId)
        }
    }

    RecipeResultScreen(
        uiState = uiState,
        onEditSettingButtonClick = backHandler,
        onNextButtonClick = viewModel::onNextButtonClick,
    )
}

@Composable
internal fun RecipeResultScreen(
    uiState: RecipeResultState,
    onEditSettingButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .statusBarsPadding(),
        ) {
            DuckeeAppBar()
            Text(
                text = "Set your Recipe \uD83D\uDCAB",
                fontWeight = FontWeight.Medium,
                style = DuckeeTheme.typography.h2,
                color = Color(0xFFFBFBFB),
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
            )
            Text(
                text = "Your Result \uD83C\uDF89",
                style = DuckeeTheme.typography.h5,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
            )
            DuckeeNetworkImage(
                model = uiState.resultImageUrl,
                contentDescription = "Result image",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(32.dp)),
            )
            DuckeeButton(
                label = "Edit settings",
                labelColor = Color(0xFFFBFBFB),
                labelStyle = DuckeeTheme.typography.paragraph4.copy(fontFamily = PromptFont),
                backgroundColor = Color.Transparent,
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 14.dp),
                onClick = onEditSettingButtonClick,
                modifier = Modifier
                    .padding(top = 28.dp)
                    .border(width = 1.dp, color = Color(0xFF49565E), shape = RoundedCornerShape(24.dp)),
            )
            Spacer(modifier = Modifier.weight(1f))
            DuckeeButton(
                label = "Next",
                labelColor = if (true) Color(0xFF08090A) else Color(0xFF7C8992),
                labelStyle = DuckeeTheme.typography.title1,
                backgroundColor = if (true) Color(0xFFFBFBFB) else Color(0xFF49565E),
                isEnabled = true,
                onClick = onNextButtonClick,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 32.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            )
        }
    }
}
