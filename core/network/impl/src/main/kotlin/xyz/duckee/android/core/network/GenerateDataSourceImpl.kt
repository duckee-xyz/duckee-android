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
package xyz.duckee.android.core.network

import com.skydoves.sandwich.ApiResponse
import xyz.duckee.android.core.network.api.GenerateAPI
import xyz.duckee.android.core.network.model.ResponseGenerateModels
import xyz.duckee.android.core.network.model.ResponseGenerateTaskStatus
import xyz.duckee.android.core.network.model.request.RequestGenerateImage
import javax.inject.Inject

internal class GenerateDataSourceImpl @Inject constructor(
    apiProvider: APIProvider,
) : GenerateDataSource {

    private val api = apiProvider[GenerateAPI::class.java]

    override suspend fun getGenerateModels(): ApiResponse<ResponseGenerateModels> =
        api.getGenerationModels()

    override suspend fun generateImage(
        isImported: Boolean,
        modelName: String,
        prompt: String,
        sizeWidth: Int,
        sizeHeight: Int,
        negativePrompt: String?,
        guidanceScale: Int?,
        runs: Int?,
        sampler: String?,
        seed: Int?,
    ): ApiResponse<ResponseGenerateTaskStatus> =
        api.generateImage(
            payload = RequestGenerateImage(
                guidanceScale = guidanceScale,
                model = RequestGenerateImage.Model(
                    importedModel = if (isImported) modelName else null,
                    servedModelName = if (isImported) null else modelName,
                    type = if (isImported) "imported" else "served",
                ),
                negativePrompt = negativePrompt,
                prompt = prompt,
                runs = runs,
                sampler = sampler,
                seed = seed,
                size = RequestGenerateImage.Size(
                    width = sizeWidth,
                    height = sizeHeight,
                ),
            ),
        )

    override suspend fun getGenerationStatus(id: String): ApiResponse<ResponseGenerateTaskStatus> =
        api.getGenerationStatus(id)
}
