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
package xyz.duckee.android.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import xyz.duckee.android.core.datastore.DuckeePreferences
import xyz.duckee.android.core.datastore.DuckeePreferencesDataSource
import xyz.duckee.android.core.datastore.DuckeePreferencesDataSourceImpl
import xyz.duckee.android.core.datastore.serializer.DuckeePreferencesSerializer
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Provides
    @Singleton
    fun providesDuckeePreferencesDataStore(
        @ApplicationContext context: Context,
        preferencesSerializer: DuckeePreferencesSerializer,
    ): DataStore<DuckeePreferences> =
        DataStoreFactory.create(
            serializer = preferencesSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        ) {
            context.dataStoreFile("duckee_preferences.pb")
        }

    @Provides
    @Singleton
    fun provideDuckeePreferencesDataSource(
        dataSourceImpl: DuckeePreferencesDataSourceImpl,
    ): DuckeePreferencesDataSource = dataSourceImpl
}
