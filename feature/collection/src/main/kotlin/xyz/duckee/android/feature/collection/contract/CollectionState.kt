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
package xyz.duckee.android.feature.collection.contract

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import xyz.duckee.android.core.ui.RandomImageUrlGenerator

@Immutable
internal data class CollectionState(
    val isLoading: Boolean = false,

    val profileImageUrl: String = RandomImageUrlGenerator.getRandomImageUrl(),

    val listed: ImmutableList<String> = List(40) {
        RandomImageUrlGenerator.getRandomImageUrl()
    }.toPersistentList(),
    val bought: ImmutableList<String> = List(9) {
        RandomImageUrlGenerator.getRandomImageUrl()
    }.toPersistentList(),
    val notForSale: ImmutableList<String> = List(61) {
        RandomImageUrlGenerator.getRandomImageUrl()
    }.toPersistentList(),
    val liked: ImmutableList<String> = List(13) {
        RandomImageUrlGenerator.getRandomImageUrl()
    }.toPersistentList(),
)
