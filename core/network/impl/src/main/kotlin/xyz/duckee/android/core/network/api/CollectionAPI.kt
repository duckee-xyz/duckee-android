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
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.duckee.android.core.network.model.ResponseArtList

internal interface CollectionAPI {

    @GET("collection/v1/user/{userId}/listed")
    suspend fun getCollectionListed(
        @Path("userId") userId: String,
        @Query("start_after") startAfter: Int?,
        @Query("limit") limit: Int?,
    ): ApiResponse<ResponseArtList>

    @GET("collection/v1/user/{userId}/bought")
    suspend fun getCollectionBought(
        @Path("userId") userId: String,
        @Query("start_after") startAfter: Int?,
        @Query("limit") limit: Int?,
    ): ApiResponse<ResponseArtList>

    @GET("collection/v1/user/{userId}/liked")
    suspend fun getCollectionLiked(
        @Path("userId") userId: String,
        @Query("start_after") startAfter: Int?,
        @Query("limit") limit: Int?,
    ): ApiResponse<ResponseArtList>
}
