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
import timber.log.Timber
import xyz.duckee.android.core.network.api.AuthAPI
import xyz.duckee.android.core.network.firebase.FirebaseAuthManager
import xyz.duckee.android.core.network.model.ResponseSignIn
import xyz.duckee.android.core.network.model.request.RequestSignIn
import javax.inject.Inject

internal class AuthDataSourceImpl @Inject constructor(
    apiProvider: APIProvider,
    private val firebaseAuthManager: FirebaseAuthManager,
) : AuthDataSource {

    private val api = apiProvider[AuthAPI::class.java]

    override suspend fun signInWithFirebase(): ApiResponse<ResponseSignIn> {
        val idToken = firebaseAuthManager.getCurrentUserIdToken().orEmpty()
        Timber.e("\uD83D\uDD25 [FirebaseUserIdToken] $idToken")

        if (idToken.isBlank()) {
            return ApiResponse.error(NullPointerException("firebase idToken == null"))
        }

        return api.signIn(
            payload = RequestSignIn(
                channel = "firebase",
                token = idToken,
            ),
        )
    }

    override suspend fun signUpWithFirebase(): ApiResponse<ResponseSignIn> {
        val idToken = firebaseAuthManager.getCurrentUserIdToken().orEmpty()

        if (idToken.isBlank()) {
            return ApiResponse.error(NullPointerException("firebase idToken == null"))
        }

        return api.signUp(
            payload = RequestSignIn(
                channel = "firebase",
                token = idToken,
            ),
        )
    }
}
