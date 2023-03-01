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
package xyz.duckee.android.core.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import xyz.duckee.android.core.model.Preferences
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DuckeePreferencesDataSourceImpl @Inject constructor(
    private val duckeePreferences: DataStore<DuckeePreferences>,
) : DuckeePreferencesDataSource {

    override val preference: Flow<Preferences> =
        duckeePreferences.data
            .map {
                Preferences(
                    accessToken = it.accessToken,
                    refreshToken = it.refreshToken,
                )
            }

    override suspend fun setCredentials(accessToken: String, refreshToken: String) {
        try {
            duckeePreferences.updateData {
                it.copy {
                    clearAccessToken()
                    clearRefreshToken()

                    this.accessToken = accessToken
                    this.refreshToken = refreshToken
                }
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    override suspend fun clearCredentials() {
        try {
            duckeePreferences.updateData {
                it.copy {
                    clearAccessToken()
                    clearRefreshToken()
                }
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
    }
}
