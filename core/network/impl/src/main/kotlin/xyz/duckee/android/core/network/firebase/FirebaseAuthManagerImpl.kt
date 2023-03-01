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
package xyz.duckee.android.core.network.firebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class FirebaseAuthManagerImpl @Inject constructor(
    @ApplicationContext context: Context,
) : FirebaseAuthManager {

    init {
        FirebaseAuth.getInstance().signOut()
        GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
            .signOut().addOnCompleteListener { }
    }

    override suspend fun getCurrentUserIdToken(): String? =
        FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.await()?.token

    override suspend fun signOut(context: Context) {
        FirebaseAuth.getInstance().signOut()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("440981286435-b04tte58tegtilqr6gr42os4d2uuaoeu.apps.googleusercontent.com")
            .requestEmail()
            .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the options specified by gso.
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();

        GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
            .signOut().addOnCompleteListener {

                googleApiClient.connect()
                googleApiClient.registerConnectionCallbacks(object :
                    GoogleApiClient.ConnectionCallbacks {
                    override fun onConnected(bundle: Bundle?) {
                        if (googleApiClient.isConnected) {
                            Auth.GoogleSignInApi.signOut(googleApiClient)
                                .setResultCallback(object : ResultCallback<Status?> {
                                    override fun onResult(status: Status) {
                                        // FIXME: restart for sign out
                                        val pm = context.packageManager
                                        val intent =
                                            pm.getLaunchIntentForPackage(context.packageName)
                                        val mainIntent =
                                            Intent.makeRestartActivityTask(intent!!.component)
                                        context.startActivity(mainIntent)
                                        Runtime.getRuntime().exit(0)
                                    }
                                })
                        }
                    }

                    override fun onConnectionSuspended(i: Int) {
                        // FIXME: restart for sign out
                        val pm = context.packageManager
                        val intent = pm.getLaunchIntentForPackage(context.packageName)
                        val mainIntent = Intent.makeRestartActivityTask(intent!!.component)
                        context.startActivity(mainIntent)
                        Runtime.getRuntime().exit(0)
                    }
                })
                // FIXME: restart for sign out
                val pm = context.packageManager
                val intent = pm.getLaunchIntentForPackage(context.packageName)
                val mainIntent = Intent.makeRestartActivityTask(intent!!.component)
                context.startActivity(mainIntent)
                Runtime.getRuntime().exit(0)
            }
    }


}
