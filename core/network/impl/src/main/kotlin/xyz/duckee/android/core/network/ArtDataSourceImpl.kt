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
import xyz.duckee.android.core.network.api.ArtAPI
import xyz.duckee.android.core.network.model.ResponseArtDetail
import xyz.duckee.android.core.network.model.ResponseArtList
import xyz.duckee.android.core.network.model.request.RequestGenerateImage
import xyz.duckee.android.core.network.model.request.RequestUploadArt
import javax.inject.Inject

internal class ArtDataSourceImpl @Inject constructor(
    apiProvider: APIProvider,
) : ArtDataSource {

    private val api = apiProvider[ArtAPI::class.java]

    override suspend fun getArtFeed(startAfter: Int?, limit: Int?, tags: String?): ApiResponse<ResponseArtList> =
        api.getArtFeed(startAfter, limit, tags)

    override suspend fun uploadArt(
        forSale: Boolean,
        imageUrl: String,
        description: String?,
        priceInFlow: Double,
        royaltyFee: Int,
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
        parentTokenId: Int?,
    ): ApiResponse<Unit> =
        api.uploadArt(
            RequestUploadArt(
                forSale = forSale,
                description = description,
                imageUrl = imageUrl,
                liked = true,
                parentTokenId = parentTokenId,
                priceInFlow = priceInFlow,
                recipe = RequestGenerateImage(
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
                royaltyFee = royaltyFee,
            ),
        )

    override suspend fun getArtDetail(tokenId: String): ApiResponse<ResponseArtDetail> =
        api.getArtDetails(tokenId)
}
