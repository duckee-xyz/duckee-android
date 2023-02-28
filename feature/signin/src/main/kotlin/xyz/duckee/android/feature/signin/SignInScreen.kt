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

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import org.orbitmvi.orbit.compose.collectSideEffect
import xyz.duckee.android.core.designsystem.DuckeeAppBar
import xyz.duckee.android.core.designsystem.DuckeeButton
import xyz.duckee.android.core.designsystem.DuckeeCharacterLoadingOverlay
import xyz.duckee.android.core.designsystem.R
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.designsystem.theme.PPObjectSans
import xyz.duckee.android.feature.signin.contract.SignInSideEffect
import xyz.duckee.android.feature.signin.contract.SignInState

@Composable
internal fun SignInRoute(
    viewModel: SignInViewModel = hiltViewModel(),
    goExploreTab: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    val googleLoginLauncher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract(),
        onResult = viewModel::onGoogleLoginResult,
    )

    viewModel.collectSideEffect {
        if (it is SignInSideEffect.OpenFirebaseGoogleLoginPrompt) {
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                .build()
            googleLoginLauncher.launch(signInIntent)
        } else if (it is SignInSideEffect.ShowErrorToast) {
            Toast.makeText(context, "Sign with google failed.", Toast.LENGTH_SHORT).show()
        } else if (it is SignInSideEffect.GoExploreTab) {
            goExploreTab()
        }
    }

    SignInScreen(
        uiState = uiState,
        onSignInGoogleButtonClick = viewModel::onSignInGoogleButtonClick,
    )
}

@Composable
internal fun SignInScreen(
    uiState: SignInState,
    onSignInGoogleButtonClick: () -> Unit,
) {
    Scaffold {
        DuckeeAppBar(
            modifier = Modifier.statusBarsPadding(),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "\uD83D\uDC24\nQwack! Qwack!\nHello Duckee!\n",
                style = DuckeeTheme.typography.h2,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
            DuckeeButton(
                label = "Sign with Google",
                labelStyle = DuckeeTheme.typography.title1.copy(
                    fontFamily = PPObjectSans,
                ),
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_google_logo),
                        contentDescription = "Google logo",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp),
                    )
                },
                onClick = onSignInGoogleButtonClick,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        if (uiState.isLoading) {
            DuckeeCharacterLoadingOverlay(loadingMessage = "Creating Account…")
        }
    }
}

@Preview(name = "Sign in screen")
@Composable
internal fun SignInScreenPreview() {
    DuckeeTheme {
        SignInScreen(
            uiState = SignInState(),
            onSignInGoogleButtonClick = {},
        )
    }
}
