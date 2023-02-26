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
package xyz.duckee.android.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import xyz.duckee.android.core.datastore.DuckeePreferencesDataSource
import xyz.duckee.android.core.network.APIProvider
import xyz.duckee.android.core.network.APIProviderImpl
import xyz.duckee.android.core.network.interceptor.APIAuthenticator
import xyz.duckee.android.core.network.interceptor.AuthorizationHeaderInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideKotlinSerializationConverterFactory(
        json: Json,
    ): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideApiResponseCallAdapterFactory(): ApiResponseCallAdapterFactory {
        return ApiResponseCallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideHttpLogInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authorizationHeaderInterceptor: AuthorizationHeaderInterceptor,
        apiAuthenticator: APIAuthenticator,
        preferencesDataSource: DuckeePreferencesDataSource,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            authenticator(apiAuthenticator)
            addInterceptor(loggingInterceptor)
            addInterceptor(authorizationHeaderInterceptor)

            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)

            runBlocking(Dispatchers.IO) {
                preferencesDataSource.preference.first().accessToken
            }.takeIf { it.isNotBlank() }?.let {
                authorizationHeaderInterceptor.setAccessToken(it)
            }
        }.build()

    @Provides
    @Singleton
    fun providesApiProvider(
        apiProviderImpl: APIProviderImpl,
    ): APIProvider = apiProviderImpl

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        apiResponseCallAdapterFactory: ApiResponseCallAdapterFactory,
    ): Retrofit = Retrofit.Builder().apply {
        client(okHttpClient)

        baseUrl(xyz.duckee.android.core.network.api.BuildConfig.API_BASE_URL)
        addConverterFactory(converterFactory)
        addCallAdapterFactory(apiResponseCallAdapterFactory)
    }.build()
}
