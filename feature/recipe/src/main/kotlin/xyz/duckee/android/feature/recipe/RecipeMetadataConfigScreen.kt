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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectSideEffect
import xyz.duckee.android.core.designsystem.DuckeeAppBar
import xyz.duckee.android.core.designsystem.DuckeeButton
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.feature.recipe.component.RecipeMetadataDescriptionItem
import xyz.duckee.android.feature.recipe.component.RecipeMetadataPriceItem
import xyz.duckee.android.feature.recipe.component.RecipeMetadataRoyaltyItem
import xyz.duckee.android.feature.recipe.component.RecipeMetadataSwitchItem
import xyz.duckee.android.feature.recipe.contract.RecipeResultMetadataState
import xyz.duckee.android.feature.recipe.contract.RecipeSideEffect

@Composable
internal fun RecipeMetadataConfigRoute(
    viewModel: RecipeMetadataConfigViewModel = hiltViewModel(),
    goSuccessScreen: () -> Unit,
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    viewModel.collectSideEffect {
        if (it is RecipeSideEffect.GoSuccessScreen) {
            goSuccessScreen()
        }
    }

    RecipeMetadataConfigScreen(
        uiState = uiState,
        onNotForSaleButtonClick = viewModel::onNotForSaleButtonClick,
        onOpenSourceButtonClick = viewModel::onOpenSourceButtonClick,
        onPriceChanged = viewModel::onPriceChange,
        onRoyaltyChanged = viewModel::onRoyaltyChanged,
        onDescriptionChanged = viewModel::onDescriptionChanged,
        onConfirmButtonClick = viewModel::onConfirmButtonClick,
    )
}

@Composable
internal fun RecipeMetadataConfigScreen(
    uiState: RecipeResultMetadataState,
    onNotForSaleButtonClick: () -> Unit,
    onOpenSourceButtonClick: () -> Unit,
    onPriceChanged: (String) -> Unit,
    onRoyaltyChanged: (Int) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onConfirmButtonClick: () -> Unit,
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .statusBarsPadding(),
        ) {
            DuckeeAppBar()
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f),
            ) {
                RecipeMetadataSwitchItem(
                    label = "\uD83D\uDCE6 Not for sale",
                    value = uiState.isNotForSale,
                    onClick = onNotForSaleButtonClick,
                )
                RecipeMetadataSwitchItem(
                    label = "\uD83C\uDD93 Open Source",
                    value = uiState.isOpenSource,
                    onClick = onOpenSourceButtonClick,
                )
                AnimatedVisibility(visible = !uiState.isNotForSale && !uiState.isOpenSource) {
                    Column {
                        Spacer(modifier = Modifier.height(20.dp))
                        Divider(
                            thickness = 1.dp,
                            color = Color(0xFF2A333A),
                        )
                        RecipeMetadataPriceItem(
                            value = uiState.price,
                            onValueChanged = onPriceChanged,
                        )
                        Divider(
                            thickness = 1.dp,
                            color = Color(0xFF2A333A),
                        )
                        RecipeMetadataRoyaltyItem(
                            value = uiState.royalty,
                            onValueChanged = onRoyaltyChanged,
                        )
                        Divider(
                            thickness = 1.dp,
                            color = Color(0xFF2A333A),
                        )
                        RecipeMetadataDescriptionItem(
                            value = uiState.description,
                            onValueChanged = onDescriptionChanged,
                        )
                        Divider(
                            thickness = 1.dp,
                            color = Color(0xFF2A333A),
                        )
                    }
                }
            }

            val isConfirmButtonEnabled by remember(uiState) {
                derivedStateOf {
                    if (uiState.isNotForSale) {
                        return@derivedStateOf true
                    }

                    if (uiState.isOpenSource) {
                        return@derivedStateOf true
                    }

                    (uiState.price.toIntOrNull() ?: 0) > 0
                }
            }

            DuckeeButton(
                label = "Confirm",
                labelColor = if (isConfirmButtonEnabled) Color(0xFF08090A) else Color(0xFF7C8992),
                labelStyle = DuckeeTheme.typography.title1,
                backgroundColor = if (isConfirmButtonEnabled) Color(0xFFFBFBFB) else Color(0xFF49565E),
                isEnabled = isConfirmButtonEnabled,
                onClick = onConfirmButtonClick,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 32.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .imePadding(),
            )
        }
    }
}
