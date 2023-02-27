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
package xyz.duckee.android.feature.explore.contract

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.duckee.android.core.model.ArtList

@Immutable
internal data class ExploreState(
    val isLoading: Boolean = true,

    val searchValue: String = "",
    val hasNext: Boolean = false,
    val nextStartAfter: String? = null,
    val feeds: ImmutableList<ArtList.Result> = persistentListOf(),

    val filters: ImmutableList<String> = persistentListOf(
        "All",
        "Anime",
        "Portait",
        "Photo",
        "Object",
        "3D",
        "Landscape",
        "Abstract",
    ),
    val selectedFilter: String = "All",
)
