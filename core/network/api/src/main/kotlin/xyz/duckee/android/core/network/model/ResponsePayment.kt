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
package xyz.duckee.android.core.network.model

import kotlinx.serialization.Serializable
import xyz.duckee.android.core.model.Payment

@Serializable
data class ResponsePayment(
    val customer: String,
    val ephemeralKey: String,
    val paymentIntent: String,
    val publishableKey: String,
)

fun ResponsePayment.toModel(): Payment =
    Payment(customer, ephemeralKey, paymentIntent, publishableKey)
