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

import androidx.lifecycle.ViewModel
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import xyz.duckee.android.core.domain.generate.GetGenerateModelsUseCase
import xyz.duckee.android.core.model.GenerationModels
import xyz.duckee.android.feature.recipe.contract.RecipeState
import javax.inject.Inject

@OptIn(OrbitExperimental::class)
@HiltViewModel
internal class RecipeViewModel @Inject constructor(
    private val getGenerateModelsUseCase: GetGenerateModelsUseCase,
) : ViewModel(), ContainerHost<RecipeState, Unit> {

    override val container = container<RecipeState, Unit>(RecipeState())

    init {
        getGenerateModels()
    }

    fun onUseModelClick(model: GenerationModels.Model) = intent {
        reduce {
            state.copy(
                selectedModel = model,
                selectedSize = null,
                selectedSampler = model.recipeDefinitions.defaultSampler,
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

    fun onGenerateButtonClick() = intent {
        reduce { state.copy(isGenerating = true) }
    }

    private fun getGenerateModels() = intent {
        getGenerateModelsUseCase()
            .suspendOnSuccess {
                reduce { state.copy(models = data.models, isLoading = false) }
            }
            .suspendOnError {
                Timber.e(message())
            }
    }
}
