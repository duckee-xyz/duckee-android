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
package xyz.duckee.android.core.domain.payment

import com.skydoves.sandwich.ApiResponse
import dagger.Reusable
import xyz.duckee.android.core.data.PaymentRepository
import xyz.duckee.android.core.model.Payment
import javax.inject.Inject

@Reusable
class PaymentArtRecipeUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository,
) {

    suspend operator fun invoke(artId: String): ApiResponse<Payment> =
        paymentRepository.paymentArtRecipe(artId)
}
