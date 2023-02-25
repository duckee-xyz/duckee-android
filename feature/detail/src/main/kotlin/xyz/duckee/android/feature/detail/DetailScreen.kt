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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.compose.collectSideEffect
import xyz.duckee.android.core.designsystem.DuckeeAppBar
import xyz.duckee.android.core.designsystem.DuckeeButton
import xyz.duckee.android.core.designsystem.DuckeeExpandable
import xyz.duckee.android.core.designsystem.DuckeeHorizontalNftCarousel
import xyz.duckee.android.core.designsystem.DuckeeInformation
import xyz.duckee.android.core.designsystem.DuckeeInformationItem
import xyz.duckee.android.core.designsystem.DuckeeLineage
import xyz.duckee.android.core.designsystem.DuckeeNetworkImage
import xyz.duckee.android.core.designsystem.DuckeePromptUnlock
import xyz.duckee.android.core.designsystem.foundation.drawColoredShadow
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.designsystem.theme.PromptFont
import xyz.duckee.android.core.ui.RandomImageUrlGenerator
import xyz.duckee.android.feature.detail.component.DetailPriceInformation
import xyz.duckee.android.feature.detail.component.DetailProfile
import xyz.duckee.android.feature.detail.contract.DetailSideEffect
import xyz.duckee.android.feature.detail.contract.DetailState
import kotlin.math.min

@Composable
internal fun DetailRoute(
    viewModel: DetailViewModel = hiltViewModel(),
    goReceiptScreen: (String) -> Unit,
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    viewModel.collectSideEffect {
        if (it is DetailSideEffect.GoReceiptScreen) {
            goReceiptScreen("1")
        }
    }

    DetailScreen(
        uiState = uiState,
        onBuyOrTryButtonClick = viewModel::onBuyOrTryButtonClick,
    )
}

@Composable
internal fun DetailScreen(
    uiState: DetailState,
    onBuyOrTryButtonClick: () -> Unit,
) {
    Scaffold {
        Box {
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
                        model = uiState.image,
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
                    profileImageUrl = "https://picsum.photos/700/700",
                    name = "ShibaSaki",
                    address = "0xh5fdfskldfsnklfsdkljdsfknldfsopjewr9u023ru90erknlsdfopij211g5",
                    isFollowed = false,
                    onClick = {},
                )
                Text(
                    text = "female Sailor moonassimilated by alien fungus, intricate Three-point lighting ortrait, by Ching Yeh and Greg Rutkowski, detailed cyberpunk in the style of GitS 1995",
                    style = DuckeeTheme.typography.paragraph4.copy(
                        fontWeight = FontWeight.ExtraLight,
                    ),
                    color = Color(0xFFFBFBFB),
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(20.dp))
                DetailPriceInformation(
                    price = 23.5,
                    soldAmount = 0,
                    royalty = 5,
                )
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "Lineage",
                    style = DuckeeTheme.typography.h6,
                    color = Color(0xFFFBFBFB),
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                val lineageImages = remember {
                    listOf(
                        RandomImageUrlGenerator.getRandomImageUrl(),
                        RandomImageUrlGenerator.getRandomImageUrl(),
                    )
                }
                DuckeeLineage(
                    parentImageUrl = lineageImages[0],
                    childImageUrl = lineageImages[1],
                )
                Spacer(modifier = Modifier.height(44.dp))
                Text(
                    text = "Derived Art NFTs",
                    style = DuckeeTheme.typography.h6,
                    color = Color(0xFFFBFBFB),
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                DuckeeHorizontalNftCarousel(
                    list = remember {
                        List(20) {
                            RandomImageUrlGenerator.getRandomImageUrl()
                        }.toPersistentList()
                    },
                )
                Spacer(modifier = Modifier.height(44.dp))
                Text(
                    text = "NFT Details",
                    style = DuckeeTheme.typography.h6,
                    color = Color(0xFFFBFBFB),
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                DuckeeInformation {
                    DuckeeInformationItem(
                        title = "Mint address",
                        value = "0x223...tp267",
                    )
                    DuckeeInformationItem(
                        title = "Token address",
                        value = "0x223...tp267",
                    )
                    DuckeeInformationItem(
                        title = "Owner",
                        value = "0x223...tp267",
                    )
                    DuckeeInformationItem(
                        title = "Generator Royalies",
                        value = "0%",
                    )
                    DuckeeInformationItem(
                        title = "Transaction Fee",
                        value = "2%",
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
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))

                var isExpanded1 by remember { mutableStateOf(false) }
                DuckeeExpandable(
                    title = "Prompt",
                    value = "female Sailor moonassimilated by alien fungus, intricate Three-point lighting ortrait, by Ching Yeh and Greg Rutkowski, detailed cyberpunk in the style of GitS 1995",
                    isExpanded = isExpanded1,
                    onClick = { isExpanded1 = !isExpanded1 },
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                var isExpanded2 by remember { mutableStateOf(false) }
                DuckeeExpandable(
                    title = "Negative Prompt",
                    value = "female Sailor moonassimilated by alien fungus, intricate Three-point lighting ortrait, by Ching Yeh and Greg Rutkowski, detailed cyberpunk in the style of GitS 1995",
                    isExpanded = isExpanded2,
                    onClick = { isExpanded2 = !isExpanded2 },
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                var isExpanded3 by remember { mutableStateOf(false) }
                DuckeeExpandable(
                    title = "Model",
                    value = "Stable Diffusion",
                    isExpanded = isExpanded3,
                    onClick = { isExpanded3 = !isExpanded3 },
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                var isExpanded4 by remember { mutableStateOf(false) }
                DuckeeExpandable(
                    title = "Size",
                    value = "female Sailor moonassimilated by alien fungus, intricate Three-point lighting ortrait, by Ching Yeh and Greg Rutkowski, detailed cyberpunk in the style of GitS 1995",
                    isExpanded = isExpanded4,
                    onClick = { isExpanded4 = !isExpanded4 },
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                DuckeeInformation {
                    DuckeeInformationItem(
                        title = "Guidance Scale",
                        value = "10",
                    )
                    DuckeeInformationItem(
                        title = "Sampler",
                        value = "DPM++ SDE Karras",
                    )
                    DuckeeInformationItem(
                        title = "Steps",
                        value = "40",
                    )
                    DuckeeInformationItem(
                        title = "Seed",
                        value = "2201719429",
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                DuckeePromptUnlock()
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
                label = "Try this Recipe",
                labelStyle = DuckeeTheme.typography.title1.copy(
                    fontFamily = PromptFont,
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
    }
}

@Preview(name = "Detail screen")
@Composable
internal fun DetailScreenPreview() {
    DuckeeTheme {
        DetailScreen(
            uiState = DetailState(),
            onBuyOrTryButtonClick = {},
        )
    }
}
