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
package xyz.duckee.android.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import xyz.duckee.android.core.domain.art.GetArtDetailsUseCase
import xyz.duckee.android.core.domain.user.FollowUserUseCase
import xyz.duckee.android.core.domain.user.GetMyProfileUseCase
import xyz.duckee.android.core.domain.user.GetUserProfileUseCase
import xyz.duckee.android.core.domain.user.UnfollowUserUseCase
import xyz.duckee.android.feature.detail.contract.DetailSideEffect
import xyz.duckee.android.feature.detail.contract.DetailState
import javax.inject.Inject

@HiltViewModel
internal class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getArtDetailsUseCase: GetArtDetailsUseCase,
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val unfollowUserUseCase: UnfollowUserUseCase,
) : ViewModel(), ContainerHost<DetailState, DetailSideEffect> {

    private val tokenId = savedStateHandle.get<String>("id").orEmpty()

    override val container = container<DetailState, DetailSideEffect>(DetailState())

    init {
        getMyProfile()
        getArtDetails()
    }

    fun onBuyOrTryButtonClick() = intent {
        postSideEffect(DetailSideEffect.GoRecipeScreen)
    }

    fun onFollowButtonClick() = intent {
        val ownerId = state.details?.owner?.id?.toString().orEmpty()

        if (state.ownerUser?.following == true) {
            unfollowUserUseCase(ownerId)
                .suspendOnSuccess {
                    getOwnerUserProfile(ownerId)
                }
        } else {
            followUserUseCase(ownerId)
                .suspendOnSuccess {
                    getOwnerUserProfile(ownerId)
                }
        }
    }

    private fun getMyProfile() = intent {
        getMyProfileUseCase()
            .suspendOnSuccess {
                reduce { state.copy(user = data) }
            }
    }

    private fun getOwnerUserProfile(id: String) = intent {
        getUserProfileUseCase(id)
            .suspendOnSuccess {
                reduce { state.copy(ownerUser = data) }
            }
    }

    private fun getArtDetails() = intent {
        reduce { state.copy(isLoading = true) }

        getArtDetailsUseCase(tokenId)
            .suspendOnSuccess {
                reduce { state.copy(isLoading = false, details = data) }

                getOwnerUserProfile(data.owner.id.toString())
            }
            .suspendOnException {
                Timber.e(exception)
            }
    }
}
