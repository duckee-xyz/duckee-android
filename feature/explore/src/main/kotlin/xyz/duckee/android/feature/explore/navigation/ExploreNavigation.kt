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
package xyz.duckee.android.feature.explore.navigation

import androidx.navigation.NavGraphBuilder
import xyz.duckee.android.core.navigation.DetailDirections
import xyz.duckee.android.core.navigation.ExploreDirections
import xyz.duckee.android.core.navigation.SignInDirections
import xyz.duckee.android.core.navigation.transition.rootTransitionComposable
import xyz.duckee.android.feature.explore.ExploreRoute

fun NavGraphBuilder.exploreScreen(
    goSignInScreen: () -> Unit,
    goDetailScreen: (String) -> Unit,
) {
    rootTransitionComposable(
        routeCommand = ExploreDirections.main,
        commands = listOf(
            SignInDirections.main,
            DetailDirections.main,
        ),
    ) {
        ExploreRoute(
            goSignInScreen = goSignInScreen,
            goDetailScreen = goDetailScreen,
        )
    }
}
