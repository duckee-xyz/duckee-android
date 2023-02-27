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
import xyz.duckee.android.core.network.api.UserAPI
import xyz.duckee.android.core.network.model.ResponseUserDetails
import javax.inject.Inject

internal class UserDataSourceImpl @Inject constructor(
    apiProvider: APIProvider,
) : UserDataSource {

    private val api = apiProvider[UserAPI::class.java]

    override suspend fun getUserMe(): ApiResponse<ResponseUserDetails> =
        api.getUserMe()

    override suspend fun getUser(id: String): ApiResponse<ResponseUserDetails> =
        api.getUser(id)

    override suspend fun followUser(id: String): ApiResponse<Unit> =
        api.followUser(id)

    override suspend fun unfollowUser(id: String): ApiResponse<Unit> =
        api.unfollowUser(id)
}
