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

import kotlinx.collections.immutable.ImmutableList

data class ArtDetails(
    val derivedTokens: ImmutableList<Token>,
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

    data class Owner(
        val address: String,
        val email: String,
        val id: Int,
        val nickname: String,
        val profileImage: String,
    )

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

        data class Model(
            val servedModelName: String,
            val type: String,
        )

        data class Size(
            val height: Int,
            val width: Int,
        )
    }
}
