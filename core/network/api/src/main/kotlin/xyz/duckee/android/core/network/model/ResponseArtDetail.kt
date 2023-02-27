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
import kotlinx.serialization.Serializable
import xyz.duckee.android.core.model.ArtDetails

@Serializable
data class ResponseArtDetail(
    val artDetails: ArtDetails,
) {
    @Serializable
    data class ArtDetails(
        val derivedTokens: List<Token>,
        val description: String,
        val forSale: Boolean,
        val imageUrl: String,
        val liked: Boolean,
        val owner: Owner,
        val parentToken: Token?,
        val priceInFlow: Int,
        val recipe: Recipe?,
        val royaltyFee: Double,
        val tokenId: Int,
    ) {
        @Serializable
        data class Owner(
            val address: String,
            val email: String,
            val id: Int,
            val nickname: String,
            val profileImage: String,
            val following: Boolean?,
        )

        @Serializable
        data class Token(
            val description: String,
            val forSale: Boolean,
            val imageUrl: String,
            val liked: Boolean,
            val owner: Owner,
            val priceInFlow: Int,
            val royaltyFee: Double,
            val tokenId: Int,
        )

        @Serializable
        data class Recipe(
            val guidanceScale: Int?,
            val model: Model,
            val negativePrompt: String?,
            val prompt: String,
            val runs: Int?,
            val sampler: String?,
            val size: Size,
            val seed: Int?,
        ) {
            @Serializable
            data class Model(
                val servedModelName: String,
                val type: String,
            )

            @Serializable
            data class Size(
                val height: Int,
                val width: Int,
            )
        }
    }
}

fun ResponseArtDetail.ArtDetails.toModel(): ArtDetails =
    ArtDetails(
        derivedTokens = derivedTokens.map { it.toModel() }.toPersistentList(),
        description = description,
        forSale = forSale,
        imageUrl = imageUrl,
        liked = liked,
        owner = owner.toModel(),
        parentToken = parentToken?.toModel(),
        priceInFlow = priceInFlow,
        recipe = recipe?.toModel(),
        royaltyFee = royaltyFee,
        tokenId = tokenId,
    )

fun ResponseArtDetail.ArtDetails.Owner.toModel(): ArtDetails.Owner =
    ArtDetails.Owner(
        address,
        email,
        id,
        nickname,
        profileImage,
        following,
    )

fun ResponseArtDetail.ArtDetails.Token.toModel(): ArtDetails.Token =
    ArtDetails.Token(
        description = description,
        forSale = forSale,
        imageUrl = imageUrl,
        liked = liked,
        owner = owner.toModel(),
        priceInFlow = priceInFlow,
        royaltyFee = royaltyFee,
        tokenId = tokenId,
    )

fun ResponseArtDetail.ArtDetails.Recipe.toModel(): ArtDetails.Recipe =
    ArtDetails.Recipe(
        guidanceScale = guidanceScale,
        model = model.toModel(),
        negativePrompt = negativePrompt,
        prompt = prompt,
        runs = runs,
        sampler = sampler,
        size = size.toModel(),
        seed = seed,
    )

fun ResponseArtDetail.ArtDetails.Recipe.Model.toModel(): ArtDetails.Recipe.Model =
    ArtDetails.Recipe.Model(servedModelName = servedModelName, type = type)

fun ResponseArtDetail.ArtDetails.Recipe.Size.toModel(): ArtDetails.Recipe.Size =
    ArtDetails.Recipe.Size(width = width, height = height)
