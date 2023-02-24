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
package xyz.duckee.android

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import soup.compose.material.motion.navigation.MaterialMotionNavHost
import soup.compose.material.motion.navigation.rememberMaterialMotionNavController
import xyz.duckee.android.core.designsystem.DuckeeBottomTab
import xyz.duckee.android.core.navigation.ExploreDirections
import xyz.duckee.android.core.navigation.exploreNavigationRoute
import xyz.duckee.android.core.navigation.navigateToExploreTab
import xyz.duckee.android.feature.explore.navigation.exploreScreen

@Composable
fun DuckeeApp() {
    val navController = rememberMaterialMotionNavController()
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(initialValue = null)
    val currentRoute by remember(navBackStackEntry) {
        derivedStateOf { navBackStackEntry?.destination?.route }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF08090A)),
    ) {
        MaterialMotionNavHost(
            navController = navController,
            startDestination = ExploreDirections.main.destination,
            enterTransition = { fadeIn(animationSpec = tween(0)) },
            exitTransition = { fadeOut(animationSpec = tween(0)) },
            modifier = Modifier.zIndex(1f),
        ) {
            exploreScreen()
        }

        DuckeeBottomTab(
            currentRoute = currentRoute.orEmpty(),
            onClick = {
                when (it) {
                    exploreNavigationRoute -> navController.navigateToExploreTab()
                }
            },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.BottomCenter)
                .zIndex(2f),
        )
    }
}
