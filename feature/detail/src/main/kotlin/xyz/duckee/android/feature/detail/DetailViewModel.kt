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
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import xyz.duckee.android.core.domain.art.GetArtDetailsUseCase
import xyz.duckee.android.core.domain.payment.PaymentArtRecipeUseCase
import xyz.duckee.android.core.domain.user.FollowUserUseCase
import xyz.duckee.android.core.domain.user.GetMyProfileUseCase
import xyz.duckee.android.core.domain.user.GetUserProfileUseCase
import xyz.duckee.android.core.domain.user.UnfollowUserUseCase
import xyz.duckee.android.core.ui.PurchaseEventManager
import xyz.duckee.android.core.ui.RecipeStore
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
    private val paymentArtRecipeUseCase: PaymentArtRecipeUseCase,
    private val recipeStore: RecipeStore,
    private val purchaseEventManager: PurchaseEventManager,
) : ViewModel(), ContainerHost<DetailState, DetailSideEffect> {

    private val tokenId = savedStateHandle.get<String>("id").orEmpty()

    override val container = container<DetailState, DetailSideEffect>(DetailState())

    init {
        getMyProfile()
        getArtDetails()

        viewModelScope.launch {
            purchaseEventManager.completeState.collect {
                getArtDetails()
            }
        }
    }

    fun onBuyOrTryButtonClick() = intent {
        if (state.details?.recipe != null) {
            postSideEffect(DetailSideEffect.GoRecipeScreen)
        } else {
            reduce { state.copy(isLoading = true) }
            paymentArtRecipeUseCase(state.details?.tokenId?.toString().orEmpty())
                .suspendOnSuccess {
                    postSideEffect(DetailSideEffect.PurchaseWithStripe(data))
                }
        }
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

                if (data.recipe != null) {
                    val recipe = mapOf(
                        "isImported" to false,
                        "modelName" to data.recipe?.model?.servedModelName.orEmpty(),
                        "prompt" to data.recipe?.prompt.orEmpty(),
                        "sizeWidth" to (data.recipe?.size?.width ?: -1),
                        "sizeHeight" to (data.recipe?.size?.height ?: -1),
                        "negativePrompt" to data.recipe?.negativePrompt.takeIf { it?.isNotBlank() == true },
                        "guidanceScale" to data.recipe?.guidanceScale.takeIf { data.recipe?.model?.servedModelName != "DallE" },
                        "runs" to data.recipe?.runs.takeIf { data.recipe?.model?.servedModelName != "DallE" },
                        "sampler" to data.recipe?.sampler.takeIf { data.recipe?.model?.servedModelName != "DallE" },
                        "seed" to data.recipe?.seed.takeIf { data.recipe?.model?.servedModelName != "DallE" }
                            ?.toInt(),
                        "parentTokenId" to data.tokenId,
                    )

                    recipeStore.saveTemporaryRecipeProperty(recipe)
                }
            }
            .suspendOnException {
                Timber.e(exception)
            }
    }
}
