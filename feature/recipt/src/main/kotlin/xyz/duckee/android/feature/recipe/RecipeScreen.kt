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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectSideEffect
import xyz.duckee.android.core.designsystem.DuckeeAppBar
import xyz.duckee.android.core.designsystem.DuckeeButton
import xyz.duckee.android.core.designsystem.DuckeeCharacterLoadingOverlay
import xyz.duckee.android.core.designsystem.DuckeeExpandable
import xyz.duckee.android.core.designsystem.DuckeeQuestionInputContainer
import xyz.duckee.android.core.designsystem.DuckeeSlider
import xyz.duckee.android.core.designsystem.DuckeeTextField
import xyz.duckee.android.core.designsystem.foundation.clickableSingle
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.designsystem.theme.PromptFont
import xyz.duckee.android.core.model.GenerationModels
import xyz.duckee.android.feature.recipe.component.RecipeAddImageButton
import xyz.duckee.android.feature.recipe.component.RecipeModelType
import xyz.duckee.android.feature.recipe.contract.RecipeSideEffect
import xyz.duckee.android.feature.recipe.contract.RecipeState

@Composable
internal fun RecipeRoute(
    viewModel: RecipeViewModel = hiltViewModel(),
    goRecipeResultScreen: (String) -> Unit,
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    viewModel.collectSideEffect {
        if (it is RecipeSideEffect.GoRecipeResultScreen) {
            goRecipeResultScreen(it.resultId)
        }
    }

    RecipeScreen(
        uiState = uiState,
        onUseModelClick = viewModel::onUseModelClick,
        onPromptValueChanged = viewModel::onPromptValueChanged,
        onNegativePromptValueChanged = viewModel::onNegativePromptValueChanged,
        onDimensionValueChanged = viewModel::onDimensionValueChanged,
        onAdvancedSettingsButtonClick = viewModel::onAdvancedSettingsButtonClick,
        onSamplerDropdownItemClick = viewModel::onSamplerDropdownItemClick,
        onOptionalSeedNumberValueChanged = viewModel::onOptionalSeedNumberValueChanged,
        onGenerateButtonClick = viewModel::onGenerateButtonClick,
    )
}

// 프롬프트 사이즈
@Composable
internal fun RecipeScreen(
    uiState: RecipeState,
    onUseModelClick: (GenerationModels.Model) -> Unit,
    onPromptValueChanged: (String) -> Unit,
    onNegativePromptValueChanged: (String) -> Unit,
    onDimensionValueChanged: (Int) -> Unit,
    onAdvancedSettingsButtonClick: () -> Unit,
    onSamplerDropdownItemClick: (String) -> Unit,
    onOptionalSeedNumberValueChanged: (String) -> Unit,
    onGenerateButtonClick: () -> Unit,
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .statusBarsPadding(),
        ) {
            val scrollState = rememberScrollState()
            val coroutineScope = rememberCoroutineScope()

            var promptPosition by remember { mutableStateOf(0f) }
            var negativePromptPosition by remember { mutableStateOf(0f) }

            val density = LocalDensity.current
            val imeHeight = WindowInsets.ime.getBottom(density)

            DuckeeAppBar()
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .navigationBarsPadding()
                            .padding(bottom = 50.dp),
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .imePadding(),
                ) {
                    Text(
                        text = "Set your Recipe \uD83D\uDCAB",
                        fontWeight = FontWeight.Medium,
                        style = DuckeeTheme.typography.h2,
                        color = Color(0xFFFBFBFB),
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    DuckeeQuestionInputContainer(
                        categoryLabel = "Import",
                        questionLabel = "Add in your image ",
                    ) {
                        RecipeAddImageButton(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    DuckeeQuestionInputContainer(
                        categoryLabel = "Model",
                        questionLabel = "Which style are you looking for?",
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 24.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            items(uiState.models) { model ->
                                RecipeModelType(
                                    imageUrl = model.thumbnail,
                                    modelLabel = model.name,
                                    isSelected = uiState.selectedModel == model,
                                    onClick = { onUseModelClick(model) },
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    DuckeeQuestionInputContainer(
                        categoryLabel = "Prompt",
                        questionLabel = "Describe your image",
                    ) {
                        DuckeeTextField(
                            value = uiState.promptValue,
                            placeHolder = "The quick brown fox jumps over the lazy dog.",
                            onValueChanged = onPromptValueChanged,
                            onFocusChanged = { focused ->
                                if (focused) {
                                    coroutineScope.launch {
                                        scrollState.scrollTo(promptPosition.toInt() - imeHeight)
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 80.dp)
                                .onGloballyPositioned { position ->
                                    if (promptPosition == 0f) {
                                        promptPosition = position.positionInWindow().y
                                    }
                                },
                        )
                    }
                    if (uiState.selectedModel?.name != "DallE") {
                        Spacer(modifier = Modifier.height(28.dp))
                        DuckeeQuestionInputContainer(
                            categoryLabel = "Negative Prompt",
                            questionLabel = "Describe things to exclude",
                        ) {
                            DuckeeTextField(
                                value = uiState.negativePromptValue,
                                placeHolder = "The quick brown fox jumps over the lazy dog.",
                                onValueChanged = onNegativePromptValueChanged,
                                onFocusChanged = { focused ->
                                    if (focused) {
                                        coroutineScope.launch {
                                            scrollState.scrollTo(negativePromptPosition.toInt() - imeHeight)
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .fillMaxWidth()
                                    .defaultMinSize(minHeight = 80.dp)
                                    .onGloballyPositioned { position ->
                                        if (negativePromptPosition == 0f) {
                                            negativePromptPosition = position.positionInWindow().y
                                        }
                                    },
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))
                    DuckeeQuestionInputContainer(
                        categoryLabel = "Dimensions",
                        questionLabel = "Decide your canvas",
                    ) {
                        DuckeeSlider(
                            steps = uiState.selectedModel?.recipeDefinitions?.availableSizes
                                ?.map { size -> "${size.width} ✕ ${size.height}" }
                                ?.toPersistentList() ?: persistentListOf("512 ✕ 512"),
                            onValueChanged = onDimensionValueChanged,
                            modifier = Modifier.padding(horizontal = 48.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    AnimatedVisibility(visible = uiState.isAdvancedPanelOpened) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            if (uiState.selectedModel?.recipeDefinitions?.maxGuidanceScale != 0) {
                                Spacer(modifier = Modifier.height(28.dp))
                                DuckeeQuestionInputContainer(
                                    categoryLabel = "Guidance Scale",
                                    questionLabel = "How much the image looks closer\n" +
                                        "to the prompt?",
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(16.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 48.dp),
                                    ) {
                                        val (selectedIndex, onSelectedIndex) = remember {
                                            mutableStateOf(
                                                uiState.selectedModel?.recipeDefinitions?.defaultGuidanceScale?.toFloat()
                                                    ?: 2f,
                                            )
                                        }

                                        Text(
                                            text = String.format("%.1f", selectedIndex),
                                            style = DuckeeTheme.typography.paragraph3,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF45A2FF),
                                        )
                                        Slider(
                                            value = selectedIndex,
                                            onValueChange = {
                                                onSelectedIndex(it)
                                            },
                                            valueRange = 2.toFloat()..(
                                                uiState.selectedModel?.recipeDefinitions?.maxGuidanceScale?.toFloat()
                                                    ?: 2f
                                                ),
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = SliderDefaults.colors(
                                                thumbColor = Color(0xFFFBFBFB),
                                                inactiveTrackColor = Color(0xFF2A333A),
                                                activeTrackColor = Color(0xFF45A2FF),
                                            ),
                                        )
                                    }
                                }
                            }

                            if (uiState.selectedModel?.recipeDefinitions?.samplers?.isNotEmpty() == true) {
                                Spacer(modifier = Modifier.height(28.dp))
                                DuckeeQuestionInputContainer(
                                    categoryLabel = "Sampler",
                                    questionLabel = "Select Scheduler",
                                ) {
                                    var isOpened by remember { mutableStateOf(false) }
                                    DuckeeExpandable(
                                        title = uiState.selectedSampler,
                                        value = "",
                                        withoutText = true,
                                        onClick = { isOpened = !isOpened },
                                        modifier = Modifier.padding(horizontal = 24.dp),
                                    )
                                    DropdownMenu(
                                        expanded = isOpened,
                                        onDismissRequest = {
                                            isOpened = false
                                        },
                                        offset = DpOffset(x = 48.dp, y = 0.dp),
                                    ) {
                                        uiState.selectedModel.recipeDefinitions.samplers.forEach {
                                            DropdownMenuItem(
                                                onClick = {
                                                    onSamplerDropdownItemClick(it)
                                                    isOpened = false
                                                },
                                                modifier = Modifier.clickableSingle {
                                                    onSamplerDropdownItemClick(it)
                                                    isOpened = false
                                                },
                                            ) {
                                                Text(it, color = Color.Black)
                                            }
                                        }
                                    }
                                }
                            }

                            if (uiState.selectedModel?.recipeDefinitions?.defaultRuns != 0) {
                                Spacer(modifier = Modifier.height(28.dp))
                                DuckeeQuestionInputContainer(
                                    categoryLabel = "Steps",
                                    questionLabel = "How many times it should run?",
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(16.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 48.dp),
                                    ) {
                                        val (selectedIndex, onSelectedIndex) = remember {
                                            mutableStateOf(
                                                uiState.selectedModel?.recipeDefinitions?.defaultRuns?.toFloat()
                                                    ?: 20f,
                                            )
                                        }

                                        Text(
                                            text = String.format("%d", selectedIndex.toInt()),
                                            style = DuckeeTheme.typography.paragraph3,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF45A2FF),
                                        )
                                        Slider(
                                            value = selectedIndex,
                                            onValueChange = {
                                                onSelectedIndex(it)
                                            },
                                            valueRange = 20f..100f,
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = SliderDefaults.colors(
                                                thumbColor = Color(0xFFFBFBFB),
                                                inactiveTrackColor = Color(0xFF2A333A),
                                                activeTrackColor = Color(0xFF45A2FF),
                                            ),
                                        )
                                    }
                                }
                            }

                            if (uiState.selectedModel?.name != "DallE") {
                                Spacer(modifier = Modifier.height(28.dp))
                                DuckeeQuestionInputContainer(
                                    categoryLabel = "Seed",
                                    questionLabel = "Enter random seed value:",
                                ) {
                                    DuckeeTextField(
                                        value = uiState.seedNumber,
                                        placeHolder = "(Optional) Seed Number",
                                        onValueChanged = onOptionalSeedNumberValueChanged,
                                        onlyNumber = true,
                                        onFocusChanged = { focused ->
                                            if (focused) {
                                                coroutineScope.launch {
                                                    scrollState.scrollTo(100000000)
                                                }
                                            }
                                        },
                                        modifier = Modifier
                                            .padding(horizontal = 24.dp)
                                            .fillMaxWidth(),
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    if (uiState.selectedModel?.name != "DallE") {
                        DuckeeButton(
                            label = if (uiState.isAdvancedPanelOpened) "Close Advanced settings" else "+  Advanced settings",
                            labelColor = Color(0xFFFBFBFB),
                            labelStyle = DuckeeTheme.typography.paragraph4.copy(fontFamily = PromptFont),
                            backgroundColor = Color.Transparent,
                            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 14.dp),
                            isEnabled = uiState.selectedModel != null,
                            onClick = onAdvancedSettingsButtonClick,
                            modifier = Modifier
                                .let { m ->
                                    if (uiState.selectedModel == null) {
                                        m.alpha(0.2f)
                                    } else {
                                        m.alpha(1.0f)
                                    }
                                }
                                .border(width = 1.dp, color = Color(0xFF49565E), shape = RoundedCornerShape(24.dp)),
                        )
                        Spacer(modifier = Modifier.height(54.dp))
                    }

                    val isGenerateButtonEnabled by remember(uiState) {
                        derivedStateOf {
                            if (uiState.selectedModel == null) {
                                return@derivedStateOf false
                            }

                            if (uiState.selectedModel.name == "DallE") {
                                return@derivedStateOf uiState.promptValue.isNotBlank()
                            }

                            uiState.promptValue.isNotBlank() && uiState.negativePromptValue.isNotBlank()
                        }
                    }

                    DuckeeButton(
                        label = "5 Credit to Generate",
                        labelColor = if (isGenerateButtonEnabled) Color(0xFF08090A) else Color(0xFF7C8992),
                        labelStyle = DuckeeTheme.typography.title1,
                        backgroundColor = if (isGenerateButtonEnabled) Color(0xFFFBFBFB) else Color(0xFF49565E),
                        isEnabled = isGenerateButtonEnabled,
                        onClick = onGenerateButtonClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                    )
                    Spacer(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .height(32.dp),
                    )
                }
            }
        }

        if (uiState.isGenerating) {
            DuckeeCharacterLoadingOverlay()
        }
    }
}

@Preview(name = "Receipt screen")
@Composable
internal fun ReceiptScreenPreview() {
    DuckeeTheme {
        RecipeScreen(
            uiState = RecipeState(),
            onUseModelClick = {},
            onPromptValueChanged = {},
            onNegativePromptValueChanged = {},
            onDimensionValueChanged = {},
            onAdvancedSettingsButtonClick = {},
            onSamplerDropdownItemClick = {},
            onOptionalSeedNumberValueChanged = {},
            onGenerateButtonClick = {},
        )
    }
}
