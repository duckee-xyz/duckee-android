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

const val receiptNavigationRoute = "receipt"

fun NavController.navigateToReceiptScreen(id: String) {
    this.navigate(
        ReceiptDirections.main.destination.replace("{id}", id),
    )
}

object ReceiptDirections {

    val main = object : NavigationCommand {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val destination: String = "$receiptNavigationRoute/{id}"
    }
}
