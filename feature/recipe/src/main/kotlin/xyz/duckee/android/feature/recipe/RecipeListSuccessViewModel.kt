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
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import xyz.duckee.android.core.ui.ExploreDataManager
import xyz.duckee.android.feature.recipe.contract.RecipeSideEffect
import javax.inject.Inject

@HiltViewModel
internal class RecipeListSuccessViewModel @Inject constructor(
    exploreDataManager: ExploreDataManager,
) : ViewModel(), ContainerHost<Unit, RecipeSideEffect> {

    override val container = container<Unit, RecipeSideEffect>(Unit)

    init {
        exploreDataManager.forceRefreshWhenEnteringExploreTab()
    }

    fun onCheckButtonClick() = intent {
        postSideEffect(RecipeSideEffect.GoMyTab)
    }

    fun onExploreButtonClick() = intent {
        postSideEffect(RecipeSideEffect.GoExploreTab)
    }
}
