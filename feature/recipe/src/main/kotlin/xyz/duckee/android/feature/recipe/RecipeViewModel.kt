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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import xyz.duckee.android.core.domain.generate.GenerateImageUseCase
import xyz.duckee.android.core.domain.generate.GetGenerateModelsUseCase
import xyz.duckee.android.core.domain.generate.GetGenerationStatusUseCase
import xyz.duckee.android.core.model.GenerationModels
import xyz.duckee.android.core.ui.RecipeStore
import xyz.duckee.android.feature.recipe.contract.RecipeSideEffect
import xyz.duckee.android.feature.recipe.contract.RecipeState
import javax.inject.Inject

@OptIn(OrbitExperimental::class)
@HiltViewModel
internal class RecipeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getGenerateModelsUseCase: GetGenerateModelsUseCase,
    private val generateImageUseCase: GenerateImageUseCase,
    private val getGenerationStatusUseCase: GetGenerationStatusUseCase,
    private val recipeStore: RecipeStore,
) : ViewModel(), ContainerHost<RecipeState, RecipeSideEffect> {

    private val isCreateMode get() = savedStateHandle.get<Int>("id") == -1
    private val isImportMode get() = savedStateHandle.get<Boolean>("importMode") == true
    private val isTryMode get() = savedStateHandle.get<Boolean>("tryMode") == true

    override val container = container<RecipeState, RecipeSideEffect>(RecipeState())

    private var checkGenerateStatusJob: Job? = null

    init {
        getGenerateModels()
        checkIsImportMode()
    }

    fun onUseModelClick(model: GenerationModels.Model) = intent {
        reduce {
            state.copy(
                selectedModel = model,
                selectedSize = model.recipeDefinitions.availableSizes.first(),
                selectedSampler = model.recipeDefinitions.defaultSampler,
                guidanceScale = model.recipeDefinitions.defaultGuidanceScale.toInt(),
                steps = model.recipeDefinitions.defaultRuns,
                seedNumber = "",
            )
        }
    }

    fun onPromptValueChanged(value: String) = blockingIntent {
        reduce { state.copy(promptValue = value) }
    }

    fun onNegativePromptValueChanged(value: String) = blockingIntent {
        reduce { state.copy(negativePromptValue = value) }
    }

    fun onDimensionValueChanged(index: Int) = blockingIntent {
        reduce {
            state.copy(
                selectedSize = state.selectedModel?.recipeDefinitions?.availableSizes?.get(index),
            )
        }
    }

    fun onAdvancedSettingsButtonClick() = intent {
        reduce { state.copy(isAdvancedPanelOpened = state.isAdvancedPanelOpened.not()) }
    }

    fun onSamplerDropdownItemClick(sampler: String) = intent {
        reduce { state.copy(selectedSampler = sampler) }
    }

    fun onOptionalSeedNumberValueChanged(value: String) = blockingIntent {
        reduce { state.copy(seedNumber = value) }
    }

    fun onGuidanceScaleValueChanged(value: Float) = blockingIntent {
        reduce { state.copy(guidanceScale = value.toInt()) }
    }

    fun onStepsValueChanged(value: Int) = blockingIntent {
        reduce { state.copy(steps = value) }
    }

    fun onGenerateButtonClick() = intent {
        reduce { state.copy(isGenerating = true) }

        val recipe = mapOf(
            "isImported" to isImportMode,
            "modelName" to state.selectedModel?.name.orEmpty(),
            "prompt" to state.promptValue,
            "sizeWidth" to (state.selectedSize?.width ?: -1),
            "sizeHeight" to (state.selectedSize?.height ?: -1),
            "negativePrompt" to state.negativePromptValue.takeIf { it.isNotBlank() },
            "guidanceScale" to state.guidanceScale.takeIf { state.selectedModel?.name != "DallE" },
            "runs" to state.steps.takeIf { state.selectedModel?.name != "DallE" },
            "sampler" to state.selectedSampler.takeIf { state.selectedModel?.name != "DallE" },
            "seed" to state.seedNumber.takeIf { state.selectedModel?.name != "DallE" && it.isNotBlank() }?.toInt(),
        )

        recipeStore.saveTemporaryRecipeProperty(recipe)

        generateImageUseCase(
            isImported = recipe["isImported"] as Boolean,
            modelName = recipe["modelName"] as String,
            prompt = recipe["prompt"] as String,
            sizeWidth = recipe["sizeWidth"] as Int,
            sizeHeight = recipe["sizeHeight"] as Int,
            negativePrompt = recipe["negativePrompt"] as? String,
            guidanceScale = recipe["guidanceScale"] as? Int,
            runs = recipe["runs"] as? Int,
            sampler = recipe["sampler"] as? String,
            seed = recipe["seed"] as? Int,
        ).suspendOnSuccess {
            val generationId = data.id
            Timber.d("➕ [GenerationID] $generationId")

            checkGenerateStatusJob = (0..Int.MAX_VALUE)
                .asSequence()
                .asFlow()
                .onEach {
                    Timber.d("⌛ Waiting generation processing...")
                    getGenerationStatusUseCase(generationId)
                        .suspendOnSuccess {
                            if (data.status == "completed") {
                                reduce { state.copy(isGenerating = false) }
                                postSideEffect(RecipeSideEffect.GoRecipeResultScreen(resultId = generationId))
                                checkGenerateStatusJob?.cancel()
                                checkGenerateStatusJob = null
                            }
                        }
                    delay(1_000)
                }
                .launchIn(viewModelScope)
        }.suspendOnError {
            // Timber.e(message())
        }.suspendOnException {
            Timber.e(exception)
        }
    }

    private fun checkIsImportMode() = intent {
        reduce { state.copy(isImportMode = isImportMode) }
    }

    private fun getGenerateModels() = intent {
        getGenerateModelsUseCase()
            .suspendOnSuccess {
                reduce {
                    state.copy(
                        models = data.models,
                        isLoading = false,
                        selectedModel = data.models.first(),
                        selectedSize = data.models.first().recipeDefinitions.availableSizes.first(),
                    )
                }

                if (isTryMode) {
                    fillTryRecipe()
                }
            }
            .suspendOnError {
                Timber.e(message())
            }
    }

    private fun fillTryRecipe() = intent {
        val tryRecipe = recipeStore.recipeState.value
        val selectedModel = state.models.first { it.name == tryRecipe["modelName"] as String }

        reduce {
            state.copy(
                selectedModel = selectedModel,
                promptValue = tryRecipe["prompt"] as String,
                negativePromptValue = (tryRecipe["negativePrompt"] as? String).orEmpty(),
                selectedSize = selectedModel.recipeDefinitions.availableSizes.first { it.width == tryRecipe["sizeWidth"] as Int },
                selectedSampler = (tryRecipe["sampler"] as? String).orEmpty(),
                seedNumber = (tryRecipe["seed"] as? Int)?.toString().orEmpty(),
                steps = (tryRecipe["runs"] as? Int) ?: 30,
            )
        }
    }
}
