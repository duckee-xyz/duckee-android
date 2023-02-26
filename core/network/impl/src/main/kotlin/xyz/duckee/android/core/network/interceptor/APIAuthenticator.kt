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
package xyz.duckee.android.core.network.interceptor

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.skydoves.sandwich.message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import xyz.duckee.android.core.datastore.DuckeePreferencesDataSource
import xyz.duckee.android.core.network.api.AuthAPI
import xyz.duckee.android.core.network.api.BuildConfig
import xyz.duckee.android.core.network.model.request.RequestRefreshToken
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class APIAuthenticator @Inject constructor(
    private val jsonConverterFactory: Converter.Factory,
    loggingInterceptor: HttpLoggingInterceptor,
    private val authorizationHeaderInterceptor: AuthorizationHeaderInterceptor,
    private val apiResponseCallAdapterFactory: ApiResponseCallAdapterFactory,
    private val preferencesDataSource: DuckeePreferencesDataSource,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == HTTP_UNAUTHORIZED_STATUS_CODE) {
            val loadCredentialTask = CoroutineScope(Dispatchers.IO).async {
                preferencesDataSource.preference.first().refreshToken
            }

            val refreshToken = runBlocking {
                loadCredentialTask.await()
            }

            val token = runBlocking {
                getNewAccessToken(refreshToken)
            }

            if (token != null) {
                return response.getSignedRequest(token)
            }
        }

        return null
    }

    private fun Response.getSignedRequest(
        token: String,
    ): Request {
        return this.request.newBuilder().removeHeader("Authorization")
            .addHeader("Authorization", "Bearer $token").build()
    }

    private suspend inline fun getNewAccessToken(token: String): String? {
        return withContext(Dispatchers.IO) {
            callRefreshAccessToken(token)
        }
    }

    private suspend inline fun callRefreshAccessToken(refreshToken: String): String? {
        val response = createWebService<AuthAPI>().refreshToken(RequestRefreshToken(refreshToken))

        if (response is ApiResponse.Success) {
            val accessToken = response.data.credentials.accessToken

            preferencesDataSource.setCredentials(accessToken, refreshToken)
            authorizationHeaderInterceptor.setAccessToken(accessToken)

            return accessToken
        } else if (response is ApiResponse.Failure) {
            Timber.e("Trying refresh accessToken, but failed.", response.message())
            return null
        }

        Timber.e("Trying refresh accessToken, but failed. (Unknown)")
        return null
    }

    private inline fun <reified T> createWebService(): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jsonConverterFactory)
            .addCallAdapterFactory(apiResponseCallAdapterFactory)
            .build()

        return retrofit.create(T::class.java)
    }

    private val okHttpClient =
        OkHttpClient.Builder().connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    private companion object {

        const val HTTP_UNAUTHORIZED_STATUS_CODE = 401
        const val TIMEOUT_SECONDS = 5L
    }
}
