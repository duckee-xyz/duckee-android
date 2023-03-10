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
package xyz.duckee.android.core.data

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.mapSuccess
import xyz.duckee.android.core.model.User
import xyz.duckee.android.core.network.UserDataSource
import xyz.duckee.android.core.network.model.toModel
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun getUserMe(): ApiResponse<User> =
        userDataSource.getUserMe().mapSuccess { userDetails.toModel() }

    override suspend fun getUser(id: String): ApiResponse<User> =
        userDataSource.getUser(id).mapSuccess { userDetails.toModel() }

    override suspend fun followUser(id: String): ApiResponse<Unit> =
        userDataSource.followUser(id)

    override suspend fun unfollowUser(id: String): ApiResponse<Unit> =
        userDataSource.unfollowUser(id)
}
