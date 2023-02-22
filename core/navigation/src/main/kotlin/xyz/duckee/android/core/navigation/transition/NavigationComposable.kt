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
package xyz.duckee.android.core.navigation.transition

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut
import soup.compose.material.motion.navigation.composable
import xyz.duckee.android.core.navigation.NavigationCommand

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.sharedXTransitionComposable(
    command: NavigationCommand,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = command.destination,
        arguments = command.arguments,
        enterTransition = { materialSharedAxisXIn(forward = true, slideDistance = 50) },
        popEnterTransition = { materialSharedAxisXIn(forward = false, slideDistance = 50) },
        exitTransition = { materialSharedAxisXOut(forward = true, slideDistance = 50) },
        popExitTransition = { materialSharedAxisXOut(forward = false, slideDistance = 50) },
        content = content,
    )
}
