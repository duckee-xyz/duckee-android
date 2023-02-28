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

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.duckee.android.core.network.ArtDataSource
import xyz.duckee.android.core.network.ArtDataSourceImpl
import xyz.duckee.android.core.network.AuthDataSource
import xyz.duckee.android.core.network.AuthDataSourceImpl
import xyz.duckee.android.core.network.CollectionDataSource
import xyz.duckee.android.core.network.CollectionDataSourceImpl
import xyz.duckee.android.core.network.GenerateDataSource
import xyz.duckee.android.core.network.GenerateDataSourceImpl
import xyz.duckee.android.core.network.PaymentDataSource
import xyz.duckee.android.core.network.PaymentDataSourceImpl
import xyz.duckee.android.core.network.UserDataSource
import xyz.duckee.android.core.network.UserDataSourceImpl

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {

    @Binds
    fun bindsAuthDataSource(
        impl: AuthDataSourceImpl,
    ): AuthDataSource

    @Binds
    fun bindsGenerateDataSource(
        impl: GenerateDataSourceImpl,
    ): GenerateDataSource

    @Binds
    fun bindsArtDataSource(
        impl: ArtDataSourceImpl,
    ): ArtDataSource

    @Binds
    fun bindsUserDataSource(
        impl: UserDataSourceImpl,
    ): UserDataSource

    @Binds
    fun bindsCollectionDataSource(
        impl: CollectionDataSourceImpl,
    ): CollectionDataSource

    @Binds
    fun bindsPaymentDataSource(
        impl: PaymentDataSourceImpl,
    ): PaymentDataSource
}
