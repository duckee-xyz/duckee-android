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
import javax.inject.Inject

internal class GenerateDataSourceImpl @Inject constructor(
    apiProvider: APIProvider,
) : GenerateDataSource {

    private val api = apiProvider[GenerateAPI::class.java]

    override suspend fun getGenerateModels(): ApiResponse<ResponseGenerateModels> =
        api.getGenerationModels()
}
