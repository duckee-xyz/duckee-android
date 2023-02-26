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
import retrofit2.http.POST
import xyz.duckee.android.core.network.model.ResponseRefreshToken
import xyz.duckee.android.core.network.model.ResponseSignIn
import xyz.duckee.android.core.network.model.request.RequestRefreshToken
import xyz.duckee.android.core.network.model.request.RequestSignIn

internal interface AuthAPI {

    @POST("auth/v1/signin")
    suspend fun signIn(@Body payload: RequestSignIn): ApiResponse<ResponseSignIn>

    @POST("auth/v1/signup")
    suspend fun signUp(@Body payload: RequestSignIn): ApiResponse<ResponseSignIn>

    @POST("auth/v1/refresh")
    suspend fun refreshToken(@Body payload: RequestRefreshToken): ApiResponse<ResponseRefreshToken>
}
