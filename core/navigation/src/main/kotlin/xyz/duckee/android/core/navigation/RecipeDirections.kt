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
package xyz.duckee.android.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

const val recipeNavigationRoute = "recipe"

fun NavController.navigateToRecipeTab(inclusive: Boolean = false) {
    this.navigate(RecipeDirections.welcome.destination) {
        popUpTo(0) {
            saveState = !inclusive
            this.inclusive = inclusive
        }

        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToRecipeScreen(id: String? = null, importMode: Boolean = false, tryMode: Boolean = false) {
    this.navigate(
        RecipeDirections.main.destination
            .replace("{id}", if (id.orEmpty().isBlank()) "-1" else id.toString())
            .replace("{importMode}", importMode.toString())
            .replace("{tryMode}", tryMode.toString()),
    )
}

fun NavController.navigateToRecipeResultScreen(id: String) {
    this.navigate(
        RecipeDirections.result.destination.replace("{id}", id),
    )
}

fun NavController.navigateToRecipeResultMetadataScreen(id: String) {
    this.navigate(
        RecipeDirections.resultMetadata.destination.replace("{id}", id),
    )
}

fun NavController.navigateToRecipeSuccessScreen() {
    this.navigate(RecipeDirections.success.destination)
}

object RecipeDirections {

    val main = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument("id") {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument("importMode") {
                type = NavType.BoolType
                defaultValue = false
            },
            navArgument("tryMode") {
                type = NavType.BoolType
                defaultValue = false
            },
        )
        override val destination: String = "$recipeNavigationRoute/{id}?importMode={importMode}&tryMode={tryMode}"
    }

    val result = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument("id") {
                type = NavType.StringType
            },
        )
        override val destination: String = "$recipeNavigationRoute/result/{id}"
    }

    val resultMetadata = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument("id") {
                type = NavType.StringType
            },
        )
        override val destination: String = "$recipeNavigationRoute/result/{id}/metadata"
    }

    val success = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val destination: String = "$recipeNavigationRoute/list-success"
    }

    val welcome = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val destination: String = recipeNavigationRoute
    }
}
