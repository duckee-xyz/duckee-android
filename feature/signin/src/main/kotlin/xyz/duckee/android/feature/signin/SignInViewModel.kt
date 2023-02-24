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
package xyz.duckee.android.feature.signin

import android.app.Activity.RESULT_OK
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import xyz.duckee.android.core.domain.auth.SignInWithGoogleUseCase
import xyz.duckee.android.core.domain.auth.SignUpWithGoogleUseCase
import xyz.duckee.android.feature.signin.contract.SignInSideEffect
import xyz.duckee.android.feature.signin.contract.SignInState
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signUpWithGoogleUseCase: SignUpWithGoogleUseCase,
) : ViewModel(), ContainerHost<SignInState, SignInSideEffect> {

    override val container = container<SignInState, SignInSideEffect>(SignInState())

    fun onSignInGoogleButtonClick() = intent {
        reduce { state.copy(isLoading = true) }
        postSideEffect(SignInSideEffect.OpenFirebaseGoogleLoginPrompt)
    }

    fun onGoogleLoginResult(result: FirebaseAuthUIAuthenticationResult) = intent {
        if (result.resultCode == RESULT_OK) {
            signInWithGoogleUseCase()
                .suspendOnSuccess {
                    Timber.d("\uD83D\uDD11 [AuthResult] sign in with google successfully")
                    Timber.d(" -> Credentials(${data.first})")
                    Timber.d(" -> User(${data.second})")

                    reduce { state.copy(isLoading = false) }
                }
                .suspendOnError {
                    signUpWithGoogleUseCase()
                        .suspendOnSuccess {
                            Timber.d("\uD83D\uDD11 [AuthResult] sign up successfully")
                            Timber.d(" -> Credentials(${data.first})")
                            Timber.d(" -> User(${data.second})")

                            reduce { state.copy(isLoading = false) }
                        }
                        .suspendOnError {
                            Timber.e(message())

                            postSideEffect(SignInSideEffect.ShowErrorToast)

                            reduce { state.copy(isLoading = false) }
                        }
                }
        } else {
            reduce { state.copy(isLoading = false) }
            Timber.e("❌ [FirebaseAuth] Error code ${result.resultCode}")
            postSideEffect(SignInSideEffect.ShowErrorToast)
        }
    }
}
