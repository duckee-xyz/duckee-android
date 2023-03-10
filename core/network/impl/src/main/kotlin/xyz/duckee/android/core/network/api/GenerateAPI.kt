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
package xyz.duckee.android.core.network.api

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xyz.duckee.android.core.network.model.ResponseGenerateModels
import xyz.duckee.android.core.network.model.ResponseGenerateTaskStatus
import xyz.duckee.android.core.network.model.request.RequestGenerateImage

internal interface GenerateAPI {

    @GET("generation/v1/models")
    suspend fun getGenerationModels(): ApiResponse<ResponseGenerateModels>

    @POST("generation/v1")
    suspend fun generateImage(@Body payload: RequestGenerateImage): ApiResponse<ResponseGenerateTaskStatus>

    @GET("generation/v1/{id}")
    suspend fun getGenerationStatus(@Path("id") id: String): ApiResponse<ResponseGenerateTaskStatus>
}
