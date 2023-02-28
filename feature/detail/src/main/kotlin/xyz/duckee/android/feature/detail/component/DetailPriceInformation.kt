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
package xyz.duckee.android.feature.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.duckee.android.core.designsystem.theme.DuckeeTheme

@Composable
internal fun DetailPriceInformation(
    modifier: Modifier = Modifier,
    price: Int,
    soldAmount: Long,
    royalty: Int,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        val density = LocalDensity.current
        var itemHeight by remember { mutableStateOf(0.dp) }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 36.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .onGloballyPositioned {
                        itemHeight = with(density) { it.size.height.toDp() }
                    }
                    .weight(1f),
            ) {
                Text(
                    text = "Listing Price",
                    style = DuckeeTheme.typography.paragraph5,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF7C8992),
                )
                Row(
                    horizontalArrangement = Arrangement
                        .spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (price > 0) {
                        Icon(
                            painter = painterResource(id = xyz.duckee.android.core.designsystem.R.drawable.icon_usdc),
                            contentDescription = null,
                            tint = Color.Unspecified,
                        )
                    }

                    Text(
                        text = if (price == 0) "Free" else "$price",
                        style = DuckeeTheme.typography.h4,
                        color = Color(0xFFFBFBFB),
                    )
                }
            }
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(itemHeight)
                    .background(
                        Color(0xFF2A333A),
                    ),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "Sold Amount",
                    style = DuckeeTheme.typography.paragraph5,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF7C8992),
                )
                Text(
                    text = "$soldAmount",
                    style = DuckeeTheme.typography.h4,
                    color = Color(0xFFFBFBFB),
                )
            }
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(itemHeight)
                    .background(
                        Color(0xFF2A333A),
                    ),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "Royalty",
                    style = DuckeeTheme.typography.paragraph5,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF7C8992),
                )
                Text(
                    text = "$royalty%",
                    style = DuckeeTheme.typography.h4,
                    color = Color(0xFFFBFBFB),
                )
            }
        }
    }
}

@Preview(name = "Detail price information component", showBackground = true, backgroundColor = 0xFF08090A)
@Composable
internal fun DetailPriceInformationPreview() {
    DuckeeTheme {
        DetailPriceInformation(
            price = 23,
            soldAmount = 0,
            royalty = 5,
        )
    }
}
