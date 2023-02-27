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
package xyz.duckee.android.feature.explore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.orbitmvi.orbit.compose.collectSideEffect
import xyz.duckee.android.core.designsystem.DuckeeFilterChip
import xyz.duckee.android.core.designsystem.DuckeeNetworkImage
import xyz.duckee.android.core.designsystem.DuckeeSearchBar
import xyz.duckee.android.core.designsystem.foundation.clickableSingle
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.core.ui.isScrolledToEnd
import xyz.duckee.android.core.ui.observeAsState
import xyz.duckee.android.feature.explore.component.ExploreImageBadge
import xyz.duckee.android.feature.explore.contract.ExploreSideEffect
import xyz.duckee.android.feature.explore.contract.ExploreState

@Composable
internal fun ExploreRoute(
    viewModel: ExploreViewModel = hiltViewModel(),
    goSignInScreen: () -> Unit,
    goDetailScreen: (String) -> Unit,
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    viewModel.collectSideEffect {
        if (it is ExploreSideEffect.GoSignInScreen) {
            goSignInScreen()
        } else if (it is ExploreSideEffect.GoDetail) {
            goDetailScreen(it.id)
        }
    }

    val lifecycle by LocalLifecycleOwner.current.lifecycle.observeAsState()
    LaunchedEffect(lifecycle) {
        if (lifecycle == Lifecycle.Event.ON_RESUME) {
            viewModel.onResume()
        }
    }

    ExploreScreen(
        uiState = uiState,
        onSearchValueChanged = viewModel::onSearchValueChanged,
        onFilterClick = viewModel::onFilterClick,
        onImageClick = viewModel::onImageClick,
        onScrollEnd = viewModel::onScrollEnd,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ExploreScreen(
    uiState: ExploreState,
    onSearchValueChanged: (String) -> Unit,
    onFilterClick: (String) -> Unit,
    onImageClick: (String) -> Unit,
    onScrollEnd: () -> Unit,
) {
    val scrollState = rememberLazyListState()

    val isScrollEnd by remember {
        derivedStateOf { scrollState.isScrolledToEnd() }
    }

    LaunchedEffect(isScrollEnd) {
        if (isScrollEnd) {
            onScrollEnd()
        }
    }

    Scaffold {
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                )
            }
        }

        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(bottom = 150.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .statusBarsPadding()
                .padding(it)
                .fillMaxSize(),
        ) {
            item {
                Text(
                    text = "\uD83D\uDC24 Explore \uD83E\uDDE0 AI\n" +
                        "Generated NFT \uD83D\uDCAB",
                    style = DuckeeTheme.typography.h1,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 24.dp, end = 24.dp),
                )
            }
            stickyHeader {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .background(Color(0xFF08090A))
                        .padding(top = 16.dp, bottom = 24.dp),
                ) {
                    DuckeeSearchBar(
                        value = uiState.searchValue,
                        onValueChanged = onSearchValueChanged,
                        placeHolder = "Search anything",
                        modifier = Modifier
                            .padding(horizontal = 24.dp),
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp),
                    ) {
                        items(uiState.filters) { filter ->
                            DuckeeFilterChip(
                                label = filter,
                                isActive = uiState.selectedFilter == filter,
                                onClick = {
                                    onFilterClick(filter)
                                },
                            )
                        }
                    }
                }
            }
            items(uiState.feeds) { feed ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(32.dp))
                        .border(width = 1.dp, color = Color(0xFF7C8992), shape = RoundedCornerShape(32.dp)),
                ) {
                    DuckeeNetworkImage(
                        model = feed.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickableSingle(onClick = { onImageClick(feed.tokenId.toString()) }),
                    )
                    if (feed.priceInFlow == 0) {
                        ExploreImageBadge(
                            label = "Open Source",
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.BottomStart),
                        )
                    } else {
                        ExploreImageBadge(
                            label = "${feed.priceInFlow}",
                            backgroundColor = Color.White.copy(alpha = 0.9f),
                            borderColor = Color.White,
                            icon = {
                                Icon(
                                    painter = painterResource(id = xyz.duckee.android.core.designsystem.R.drawable.icon_usdc),
                                    contentDescription = "duck logo",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(24.dp),
                                )
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.BottomStart),
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Explore screen")
@Composable
internal fun ExploreScreenPreview() {
    DuckeeTheme {
        ExploreScreen(
            uiState = ExploreState(),
            onSearchValueChanged = {},
            onFilterClick = {},
            onImageClick = {},
            onScrollEnd = {},
        )
    }
}
