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
import xyz.duckee.android.core.model.Credentials
import xyz.duckee.android.core.model.User
import xyz.duckee.android.core.network.AuthDataSource
import xyz.duckee.android.core.network.model.toModel
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource,
) : AuthRepository {

    override suspend fun signInWithFirebase(): ApiResponse<Pair<Credentials, User>> =
        dataSource.signInWithFirebase()
            .mapSuccess { credentials.toModel() to user.toModel() }

    override suspend fun signUpWithFirebase(): ApiResponse<Pair<Credentials, User>> =
        dataSource.signUpWithFirebase()
            .mapSuccess { credentials.toModel() to user.toModel() }
}
