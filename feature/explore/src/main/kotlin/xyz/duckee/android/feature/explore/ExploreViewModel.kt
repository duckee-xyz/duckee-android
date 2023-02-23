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
package xyz.duckee.android.feature.explore

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import xyz.duckee.android.feature.explore.contract.ExploreState
import javax.inject.Inject

@HiltViewModel
internal class ExploreViewModel @Inject constructor() : ViewModel(), ContainerHost<ExploreState, Unit> {

    override val container = container<ExploreState, Unit>(ExploreState())

    @OptIn(OrbitExperimental::class)
    fun onSearchValueChanged(value: String) = blockingIntent {
        reduce { state.copy(searchValue = value) }
    }

    fun onFilterClick(filter: String) = intent {
        reduce { state.copy(selectedFilter = filter) }
    }
}
