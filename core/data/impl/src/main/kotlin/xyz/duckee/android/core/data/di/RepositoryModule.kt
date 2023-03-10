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
package xyz.duckee.android.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.duckee.android.core.data.ArtRepository
import xyz.duckee.android.core.data.ArtRepositoryImpl
import xyz.duckee.android.core.data.AuthRepository
import xyz.duckee.android.core.data.AuthRepositoryImpl
import xyz.duckee.android.core.data.CollectionRepository
import xyz.duckee.android.core.data.CollectionRepositoryImpl
import xyz.duckee.android.core.data.GenerateRepository
import xyz.duckee.android.core.data.GenerateRepositoryImpl
import xyz.duckee.android.core.data.PaymentRepository
import xyz.duckee.android.core.data.PaymentRepositoryImpl
import xyz.duckee.android.core.data.PreferencesRepository
import xyz.duckee.android.core.data.PreferencesRepositoryImpl
import xyz.duckee.android.core.data.UserRepository
import xyz.duckee.android.core.data.UserRepositoryImpl

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsAuthRepository(
        impl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    fun bindsGenerateRepository(
        impl: GenerateRepositoryImpl,
    ): GenerateRepository

    @Binds
    fun bindsPreferencesRepository(
        impl: PreferencesRepositoryImpl,
    ): PreferencesRepository

    @Binds
    fun bindsArtRepository(
        impl: ArtRepositoryImpl,
    ): ArtRepository

    @Binds
    fun bindsUserRepository(
        impl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    fun bindsCollectionRepository(
        impl: CollectionRepositoryImpl,
    ): CollectionRepository

    @Binds
    fun bindsPaymentRepository(
        impl: PaymentRepositoryImpl,
    ): PaymentRepository
}
