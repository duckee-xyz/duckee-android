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
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import xyz.duckee.android.feature.recipe.contract.RecipeResultMetadataState
import javax.inject.Inject

@OptIn(OrbitExperimental::class)
@HiltViewModel
internal class RecipeMetadataConfigViewModel @Inject constructor() :
    ViewModel(),
    ContainerHost<RecipeResultMetadataState, Unit> {

    override val container = container<RecipeResultMetadataState, Unit>(RecipeResultMetadataState())

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
        val onlyNumberRegex = "^[0-9]*\$".toRegex()

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
    }
}
