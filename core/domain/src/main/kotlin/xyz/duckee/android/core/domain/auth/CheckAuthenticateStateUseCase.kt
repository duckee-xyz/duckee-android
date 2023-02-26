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
package xyz.duckee.android.core.domain.auth

import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import xyz.duckee.android.core.data.PreferencesRepository
import javax.inject.Inject

@Reusable
class CheckAuthenticateStateUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {

    suspend operator fun invoke(): Boolean =
        withContext(Dispatchers.IO) {
            val pref = preferencesRepository.preference.first()

            pref.accessToken.isNotBlank() && pref.refreshToken.isNotBlank()
        }
}
