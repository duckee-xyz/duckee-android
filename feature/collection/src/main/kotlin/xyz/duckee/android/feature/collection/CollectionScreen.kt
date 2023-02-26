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
package xyz.duckee.android.feature.collection

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import timber.log.Timber
import xyz.duckee.android.core.designsystem.DuckeeScrollableTabRow
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme
import xyz.duckee.android.feature.collection.component.CollectionBalance
import xyz.duckee.android.feature.collection.component.CollectionProfile
import xyz.duckee.android.feature.collection.component.CollectionTitleBar
import xyz.duckee.android.feature.collection.component.CollectionWallet
import xyz.duckee.android.feature.collection.contract.CollectionState
import xyz.duckee.android.feature.collection.ui.pagerTabIndicatorOffset

private val tabs = listOf("Listed", "Bought", "Not for sale", "Liked")

@Composable
internal fun CollectionRoute(
    viewModel: CollectionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    CollectionScreen(
        uiState = uiState,
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CollectionScreen(
    uiState: CollectionState,
) {
    val state = rememberCollapsingToolbarScaffoldState()
    val density = LocalDensity.current

    val pagerState = rememberPagerState()
    var selectedTabIndex by remember { mutableStateOf(0) }

    CollapsingToolbarScaffold(
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val scrollProgress = with(density) { state.toolbarState.height.toDp() }
            Timber.e(scrollProgress.toString())

            Box(
                modifier = Modifier
                    .background(Color(0xFF08090A))
                    .fillMaxWidth()
                    .height(320.dp)
                    .pin(),
            )

            CollectionTitleBar(
                onSettingClick = {},
                modifier = Modifier
                    .height(50.dp)
                    .offset(y = -(320f - scrollProgress.value).dp),
            )

            Column(
                modifier = Modifier
                    .padding(top = 58.dp)
                    .offset(y = -(320f - scrollProgress.value).dp),
            ) {
                CollectionProfile(
                    profileUrl = uiState.profileImageUrl,
                    recipeCount = 123,
                    followingCount = 1234,
                    followerCount = 12345,
                )
                Spacer(modifier = Modifier.height(12.dp))
                CollectionWallet(
                    profileName = "ShibaSaki",
                    address = "0xh5f...211g5",
                    onLinkButtonClick = {},
                )
                Spacer(modifier = Modifier.height(12.dp))
                CollectionBalance(
                    balance = 14910.29,
                    estimateBalance = 1134.0,
                    onAddButtonClick = {},
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                DuckeeScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Color.Transparent,
                    edgePadding = 24.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .pagerTabIndicatorOffset(pagerState, tabPositions)
                                .padding(end = 20.dp),
                            height = 3.dp,
                            color = Color.White,
                        )
                    },
                ) {
                    tabs.forEachIndexed { index, s ->
                        val colorValue by animateColorAsState(
                            targetValue = if ((pagerState.currentPage) == index) {
                                Color.White
                            } else {
                                Color(
                                    0xFF49565E,
                                )
                            },
                        )
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {},
                            modifier = Modifier
                                .padding(end = 20.dp),
                        ) {
                            Text(
                                text = s,
                                style = DuckeeTheme.typography.h6,
                                color = colorValue,
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                            )
                        }
                    }
                }
                Divider(
                    thickness = 1.dp,
                    color = Color(0xFF171C20),
                    modifier = Modifier.offset(y = (-1).dp),
                )
            }
        },
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
    ) {
        HorizontalPager(
            count = 4,
            state = pagerState,
        ) { page ->
            LazyColumn(
                contentPadding = PaddingValues(18.dp),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                items(100) {
                    Text(
                        text = "Item $it",
                        color = Color.White,
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        }
    }
}
