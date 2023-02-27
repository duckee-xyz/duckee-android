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
package xyz.duckee.android.feature.detail.contract

import xyz.duckee.android.core.model.ArtDetails
import xyz.duckee.android.core.model.User
import javax.annotation.concurrent.Immutable

@Immutable
internal data class DetailState(
    val isLoading: Boolean = false,
    val details: ArtDetails? = null,
    val user: User? = null,
)
