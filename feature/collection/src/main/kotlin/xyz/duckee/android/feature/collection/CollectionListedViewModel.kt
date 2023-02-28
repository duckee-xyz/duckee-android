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
package xyz.duckee.android.feature.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import xyz.duckee.android.core.domain.collection.GetCollectionListedUseCase
import xyz.duckee.android.core.domain.user.GetMyProfileUseCase
import xyz.duckee.android.core.ui.CollectionDataManager
import xyz.duckee.android.feature.collection.contract.CollectionFeedState
import javax.inject.Inject

@HiltViewModel
internal class CollectionListedViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getCollectionListedUseCase: GetCollectionListedUseCase,
    private val collectionDataManager: CollectionDataManager,
) : ViewModel(), ContainerHost<CollectionFeedState, Unit> {

    override val container = container<CollectionFeedState, Unit>(CollectionFeedState())

    init {
        viewModelScope.launch {
            getArtFeed()
        }
    }

    fun onResume() = intent {
        if (collectionDataManager.reloadState.value) {
            reduce { CollectionFeedState() }
            getArtFeed()
        }
    }

    private fun getArtFeed() = intent {
        if (state.nextStartAfter == null) {
            reduce { state.copy(isLoading = true) }
        }

        getMyProfileUseCase()
            .suspendOnSuccess {
                getCollectionListedUseCase(
                    userId = data.id.toString(),
                    startAfter = state.nextStartAfter.takeIf { !it.isNullOrBlank() }?.toInt(),
                ).suspendOnSuccess {
                    reduce {
                        state.copy(
                            isLoading = false,
                            hasNext = data.hasNext,
                            nextStartAfter = data.nextStartAfter.orEmpty(),
                            feeds = buildList {
                                addAll(state.feeds)
                                addAll(data.results)
                            }.toPersistentList(),
                        )
                    }
                }
            }
    }
}
