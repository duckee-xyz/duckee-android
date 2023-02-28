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
package xyz.duckee.android.feature.detail

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import kotlin.math.min
import org.orbitmvi.orbit.compose.collectSideEffect
import xyz.duckee.android.core.designsystem.DuckeeAppBar
import xyz.duckee.android.core.designsystem.DuckeeButton
import xyz.duckee.android.core.designsystem.DuckeeCharacterLoadingOverlay
import xyz.duckee.android.core.designsystem.DuckeeExpandable
import xyz.duckee.android.core.designsystem.DuckeeHorizontalNftCarousel
import xyz.duckee.android.core.designsystem.DuckeeInformation
import xyz.duckee.android.core.designsystem.DuckeeInformationItem
import xyz.duckee.android.core.designsystem.DuckeeLineage
import xyz.duckee.android.core.designsystem.DuckeeNetworkImage
import xyz.duckee.android.core.designsystem.DuckeePromptUnlock
import xyz.duckee.android.core.designsystem.foundation.drawColoredShadow
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.designsystem.theme.PPObjectSans
import xyz.duckee.android.core.ui.LocalPaymentSheet
import xyz.duckee.android.feature.detail.component.DetailPriceInformation
import xyz.duckee.android.feature.detail.component.DetailProfile
import xyz.duckee.android.feature.detail.contract.DetailSideEffect
import xyz.duckee.android.feature.detail.contract.DetailState

@Composable
internal fun DetailRoute(
    viewModel: DetailViewModel = hiltViewModel(),
    goRecipeScreen: () -> Unit,
) {
    val context = LocalContext.current as ComponentActivity
    val paymentSheet = LocalPaymentSheet.current
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    viewModel.collectSideEffect {
        if (it is DetailSideEffect.GoRecipeScreen) {
            goRecipeScreen()
        } else if (it is DetailSideEffect.PurchaseWithStripe) {
            val paymentIntentClientSecret = it.payment.paymentIntent
            val customerConfiguration = PaymentSheet.CustomerConfiguration(
                it.payment.customer,
                it.payment.ephemeralKey,
            )
            PaymentConfiguration.init(context, it.payment.publishableKey)

            paymentSheet?.presentWithPaymentIntent(
                paymentIntentClientSecret,
                PaymentSheet.Configuration(
                    "Duckee Art Recipe",
                    customer = customerConfiguration,
                    allowsDelayedPaymentMethods = true,
                ),
            )
        }
    }

    DetailScreen(
        uiState = uiState,
        onBuyOrTryButtonClick = viewModel::onBuyOrTryButtonClick,
        onFollowButtonClick = viewModel::onFollowButtonClick,
    )
}

@Composable
internal fun DetailScreen(
    uiState: DetailState,
    onBuyOrTryButtonClick: () -> Unit,
    onFollowButtonClick: () -> Unit,
) {
    Scaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            val scrollState = rememberScrollState()
            val density = LocalDensity.current
            var appBarBackgroundColorAlpha by remember { mutableStateOf(Color.Black.copy(0f)) }

            LaunchedEffect(scrollState.value) {
                val scrollStateDp = with(density) { scrollState.value.toDp() }

                val progress = (scrollStateDp / 440.dp)
                appBarBackgroundColorAlpha = Color.Black.copy(min(progress, 1f))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(scrollState),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(440.dp)
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                ) {
                    DuckeeNetworkImage(
                        model = uiState.details?.imageUrl.orEmpty(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(appBarBackgroundColorAlpha),
                    )
                }
                DetailProfile(
                    profileImageUrl = uiState.details?.owner?.profileImage.orEmpty(),
                    name = uiState.details?.owner?.nickname.orEmpty(),
                    address = uiState.details?.owner?.address.orEmpty(),
                    hideFollowButton = uiState.user?.address == uiState.details?.owner?.address,
                    isFollowed = uiState.ownerUser?.following == true,
                    onClick = onFollowButtonClick,
                )
                Text(
                    text = uiState.details?.description ?: "No Description",
                    style = DuckeeTheme.typography.paragraph4.copy(
                        fontWeight = FontWeight.ExtraLight,
                    ),
                    color = Color(0xFFFBFBFB),
                    modifier = Modifier.padding(horizontal = 12.dp),
                )
                Spacer(modifier = Modifier.height(20.dp))
                DetailPriceInformation(
                    price = uiState.details?.priceInFlow ?: 0.0,
                    soldAmount = 0,
                    royalty = uiState.details?.royaltyFee?.toInt() ?: 0,
                )
                Spacer(modifier = Modifier.height(28.dp))

                if (uiState.details?.parentToken != null) {
                    Text(
                        text = "Lineage",
                        style = DuckeeTheme.typography.h6,
                        color = Color(0xFFFBFBFB),
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    DuckeeLineage(
                        parentImageUrl = uiState.details.parentToken?.imageUrl.orEmpty(),
                        childImageUrl = uiState.details.imageUrl,
                    )
                    Spacer(modifier = Modifier.height(44.dp))
                }

                if (uiState.details?.derivedTokens?.isEmpty() == false) {
                    Text(
                        text = "Derived Art NFTs",
                        style = DuckeeTheme.typography.h6,
                        color = Color(0xFFFBFBFB),
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    DuckeeHorizontalNftCarousel(
                        tokens = uiState.details.derivedTokens,
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }

                Text(
                    text = "NFT Details",
                    style = DuckeeTheme.typography.h6,
                    color = Color(0xFFFBFBFB),
                    modifier = Modifier.padding(horizontal = 12.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                DuckeeInformation {
                    DuckeeInformationItem(
                        title = "Mint Address",
                        value = "0xbda...f6a6e",
                    )
                    DuckeeInformationItem(
                        title = "Owner",
                        value = uiState.details?.owner?.address.orEmpty().run {
                            if (length > 10) {
                                substring(0, 5) + "..." + substring(length - 5, length)
                            } else {
                                ""
                            }
                        },
                    )
                    DuckeeInformationItem(
                        title = "Generator Royalies",
                        value = "${uiState.details?.royaltyFee}%",
                    )
                    DuckeeInformationItem(
                        title = "Listing/Biding/Cancel",
                        value = "Free",
                    )
                }
                Spacer(modifier = Modifier.height(44.dp))
                Text(
                    text = "Recipe",
                    style = DuckeeTheme.typography.h6,
                    color = Color(0xFFFBFBFB),
                    modifier = Modifier.padding(horizontal = 12.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.details?.recipe == null) {
                    DuckeePromptUnlock()
                } else {
                    var isExpanded1 by remember { mutableStateOf(true) }
                    DuckeeExpandable(
                        title = "Prompt",
                        value = uiState.details.recipe?.prompt.orEmpty(),
                        isExpanded = isExpanded1,
                        onClick = { isExpanded1 = !isExpanded1 },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (uiState.details.recipe?.negativePrompt != null) {
                        var isExpanded2 by remember { mutableStateOf(false) }
                        DuckeeExpandable(
                            title = "Negative Prompt",
                            value = "female Sailor moonassimilated by alien fungus, intricate Three-point lighting ortrait, by Ching Yeh and Greg Rutkowski, detailed cyberpunk in the style of GitS 1995",
                            isExpanded = isExpanded2,
                            onClick = { isExpanded2 = !isExpanded2 },
                            modifier = Modifier.padding(horizontal = 12.dp),
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    var isExpanded3 by remember { mutableStateOf(false) }
                    DuckeeExpandable(
                        title = "Model",
                        value = uiState.details.recipe?.model?.servedModelName.orEmpty(),
                        isExpanded = isExpanded3,
                        onClick = { isExpanded3 = !isExpanded3 },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    var isExpanded4 by remember { mutableStateOf(false) }
                    DuckeeExpandable(
                        title = "Size",
                        value = "${uiState.details.recipe?.size?.width} x ${uiState.details.recipe?.size?.height}",
                        isExpanded = isExpanded4,
                        onClick = { isExpanded4 = !isExpanded4 },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (uiState.details.recipe?.model?.servedModelName != "DallE") {
                        DuckeeInformation {
                            DuckeeInformationItem(
                                title = "Guidance Scale",
                                value = "${uiState.details.recipe?.guidanceScale}",
                            )
                            DuckeeInformationItem(
                                title = "Sampler",
                                value = "${uiState.details.recipe?.sampler}",
                            )
                            DuckeeInformationItem(
                                title = "Steps",
                                value = "${uiState.details.recipe?.runs}",
                            )

                            if (uiState.details.recipe?.seed != null) {
                                DuckeeInformationItem(
                                    title = "Seed",
                                    value = "${uiState.details.recipe?.seed}",
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(150.dp))
            }

            DuckeeAppBar(
                isBackButtonDimEnabled = true,
                modifier = Modifier
                    .background(appBarBackgroundColorAlpha)
                    .align(Alignment.TopCenter)
                    .statusBarsPadding(),
            )

            DuckeeButton(
                label = if (uiState.details?.recipe == null) {
                    "Buy To Unlock This Recipe"
                } else {
                    "Try This Recipe"
                },
                labelStyle = DuckeeTheme.typography.title1.copy(
                    fontFamily = PPObjectSans,
                    fontWeight = FontWeight.Medium,
                ),
                onClick = onBuyOrTryButtonClick,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp)
                    .drawColoredShadow(
                        color = Color.Black,
                        alpha = 0.3f,
                        shadowRadius = 12.dp,
                        offsetY = 4.dp,
                        borderRadius = 40.dp,
                    )
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            )
        }

        if (uiState.isLoading) {
            DuckeeCharacterLoadingOverlay(loadingMessage = "Printing some USDC… ")
        }
    }
}

@Preview(name = "Detail screen")
@Composable
internal fun DetailScreenPreview() {
    DuckeeTheme {
        DetailScreen(
            uiState = DetailState(),
            onBuyOrTryButtonClick = {},
            onFollowButtonClick = {},
        )
    }
}
