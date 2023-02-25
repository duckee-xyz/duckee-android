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
package xyz.duckee.android.core.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class GenerationModels(
    val models: ImmutableList<Model>,
) {

    @Immutable
    data class Model(
        val description: String,
        val name: String,
        val recipeDefinitions: RecipeDefinitions,
        val thumbnail: String,
        val title: String,
        val version: String,
    ) {

        @Immutable
        data class RecipeDefinitions(
            val availableSizes: ImmutableList<AvailableSize>,
            val defaultGuidanceScale: Double,
            val defaultRuns: Int,
            val defaultSampler: String,
            val maxGuidanceScale: Int,
            val promptInputOnly: Boolean,
            val samplers: ImmutableList<String>,
        ) {

            @Immutable
            data class AvailableSize(
                val height: Int,
                val width: Int,
            )
        }
    }
}
