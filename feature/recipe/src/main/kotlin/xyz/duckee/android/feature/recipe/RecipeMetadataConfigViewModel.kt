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
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import xyz.duckee.android.core.domain.art.UploadArtUseCase
import xyz.duckee.android.core.domain.generate.GetGenerationStatusUseCase
import xyz.duckee.android.core.model.GenerateTaskStatus
import xyz.duckee.android.core.ui.RecipeStore
import xyz.duckee.android.feature.recipe.contract.RecipeResultMetadataState
import xyz.duckee.android.feature.recipe.contract.RecipeSideEffect
import javax.inject.Inject

@OptIn(OrbitExperimental::class)
@HiltViewModel
internal class RecipeMetadataConfigViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGenerationStatusUseCase: GetGenerationStatusUseCase,
    private val uploadArtUseCase: UploadArtUseCase,
    private val recipeStore: RecipeStore,
) : ViewModel(), ContainerHost<RecipeResultMetadataState, RecipeSideEffect> {

    override val container = container<RecipeResultMetadataState, RecipeSideEffect>(RecipeResultMetadataState())

    private val resultId = savedStateHandle.get<String>("id").orEmpty()
    private var generateResult: GenerateTaskStatus? = null

    init {
        getGenerationStatus()
    }

    fun onNotForSaleButtonClick() = intent {
        reduce { state.copy(isNotForSale = state.isNotForSale.not()) }

        if (state.isNotForSale) {
            reduce { state.copy(isOpenSource = false) }
        }
    }

    fun onOpenSourceButtonClick() = intent {
        reduce { state.copy(isOpenSource = state.isOpenSource.not()) }

        if (state.isOpenSource) {
            reduce { state.copy(isNotForSale = false) }
        }
    }

    fun onPriceChange(price: String) = blockingIntent {
        val onlyNumberRegex = "^[0-9]*(\\.[0-9]{1,4})?\$".toRegex()

        if (onlyNumberRegex.matches(price)) {
            reduce { state.copy(price = price) }
        }
    }

    fun onRoyaltyChanged(royalty: Int) = blockingIntent {
        reduce { state.copy(royalty = royalty) }
    }

    fun onDescriptionChanged(value: String) = blockingIntent {
        reduce { state.copy(description = value) }
    }

    fun onConfirmButtonClick() = intent {
        reduce { state.copy(isLoading = true) }

        val recipe = recipeStore.recipeState.value

        uploadArtUseCase(
            forSale = !state.isNotForSale,
            imageUrl = generateResult?.resultImageUrl.orEmpty(),
            description = state.description,
            priceInFlow = state.price.takeIf { !state.isNotForSale && !state.isOpenSource }?.toDouble() ?: 0.0,
            royaltyFee = state.royalty.takeIf { !state.isNotForSale && !state.isOpenSource }?.toInt() ?: 0,
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
            parentTokenId = recipe["parentTokenId"] as? Int,
        ).suspendOnSuccess {
            postSideEffect(RecipeSideEffect.GoSuccessScreen)
        }
    }

    private fun getGenerationStatus() = intent {
        getGenerationStatusUseCase(resultId)
            .suspendOnSuccess {
                generateResult = data
            }
    }
}
