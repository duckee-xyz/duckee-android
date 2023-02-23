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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.duckee.android.core.designsystem.DuckeeFilterChip
import xyz.duckee.android.core.designsystem.DuckeeNetworkImage
import xyz.duckee.android.core.designsystem.DuckeeSearchBar
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.feature.explore.contract.ExploreState

@Composable
internal fun ExploreRoute(
    viewModel: ExploreViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    ExploreScreen(
        uiState = uiState,
        onSearchValueChanged = viewModel::onSearchValueChanged,
        onFilterClick = viewModel::onFilterClick,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ExploreScreen(
    uiState: ExploreState,
    onSearchValueChanged: (String) -> Unit,
    onFilterClick: (String) -> Unit,
) {
    Scaffold {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
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
                        modifier = Modifier.padding(horizontal = 24.dp),
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
            items(uiState.randomImages) { image ->
                DuckeeNetworkImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(32.dp))
                        .border(width = 1.dp, color = Color(0xFF7C8992), shape = RoundedCornerShape(32.dp)),
                )
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
        )
    }
}
