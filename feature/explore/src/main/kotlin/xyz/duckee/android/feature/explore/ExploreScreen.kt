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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ExploreScreen(
    uiState: ExploreState,
    onSearchValueChanged: (String) -> Unit,
) {
    Scaffold {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            stickyHeader {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                ) {
                    Text(
                        text = "\uD83D\uDC24 Explore \uD83E\uDDE0 AI\n" +
                            "Generated NFT \uD83D\uDCAB",
                        style = DuckeeTheme.typography.h1,
                        color = Color.White,
                    )
                    DuckeeSearchBar(
                        value = uiState.searchValue,
                        onValueChanged = onSearchValueChanged,
                        placeHolder = "Search anything",
                    )
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
        )
    }
}
