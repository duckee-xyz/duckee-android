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
package xyz.duckee.android.core.network.model

import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.duckee.android.core.model.GenerationModels

@Serializable
data class ResponseGenerateModels(
    @SerialName("models")
    val models: List<Model>,
) {
    @Serializable
    data class Model(
        @SerialName("description")
        val description: String,
        @SerialName("name")
        val name: String,
        @SerialName("recipeDefinitions")
        val recipeDefinitions: RecipeDefinitions,
        @SerialName("thumbnail")
        val thumbnail: String,
        @SerialName("title")
        val title: String,
        @SerialName("version")
        val version: String,
    ) {
        @Serializable
        data class RecipeDefinitions(
            @SerialName("availableSizes")
            val availableSizes: List<AvailableSize>,
            @SerialName("defaultGuidanceScale")
            val defaultGuidanceScale: Double,
            @SerialName("defaultRuns")
            val defaultRuns: Int,
            @SerialName("defaultSampler")
            val defaultSampler: String,
            @SerialName("maxGuidanceScale")
            val maxGuidanceScale: Int,
            @SerialName("promptInputOnly")
            val promptInputOnly: Boolean,
            @SerialName("samplers")
            val samplers: List<String>,
        ) {
            @Serializable
            data class AvailableSize(
                @SerialName("height")
                val height: Int,
                @SerialName("width")
                val width: Int,
            )
        }
    }
}

fun ResponseGenerateModels.toModel(): GenerationModels =
    GenerationModels(
        models = models.map { it.toModel() }.toPersistentList(),
    )

fun ResponseGenerateModels.Model.toModel(): GenerationModels.Model =
    GenerationModels.Model(
        description = description,
        name = name,
        recipeDefinitions = recipeDefinitions.toModel(),
        thumbnail = thumbnail,
        title = title,
        version = version,
    )

fun ResponseGenerateModels.Model.RecipeDefinitions.toModel(): GenerationModels.Model.RecipeDefinitions =
    GenerationModels.Model.RecipeDefinitions(
        availableSizes = availableSizes.map { it.toModel() }.toPersistentList(),
        defaultGuidanceScale = defaultGuidanceScale,
        defaultRuns = defaultRuns,
        defaultSampler = defaultSampler,
        maxGuidanceScale = maxGuidanceScale,
        promptInputOnly = promptInputOnly,
        samplers = samplers.toPersistentList(),
    )

fun ResponseGenerateModels.Model.RecipeDefinitions.AvailableSize.toModel(): GenerationModels.Model.RecipeDefinitions.AvailableSize =
    GenerationModels.Model.RecipeDefinitions.AvailableSize(width = width, height = height)
