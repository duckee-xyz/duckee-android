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
package xyz.duckee.android.core.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import xyz.duckee.android.core.designsystem.foundation.placeholder

@Composable
fun DuckeeNetworkImage(
    modifier: Modifier = Modifier,
    overlay: @Composable () -> Unit = {},
    model: Any?,
    contentDescription: String?,
    shape: Shape = RectangleShape,
    contentScale: ContentScale = ContentScale.Crop,
    useProgressIndicatorInsteadPlaceHolder: Boolean = false,
    onImageLoaded: () -> Unit = {},
) {
    val cachedOverlay by rememberUpdatedState(overlay)

    if (LocalInspectionMode.current) {
        Box {
            ImageLoadingPlaceHolder(
                shape = shape,
                useProgressIndicatorInsteadPlaceHolder = useProgressIndicatorInsteadPlaceHolder,
                modifier = modifier,
            )
            cachedOverlay()
        }
    } else {
        SubcomposeAsyncImage(
            model = model,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
        ) {
            val state = painter.state

            LaunchedEffect(state) {
                if (state is AsyncImagePainter.State.Success) {
                    onImageLoaded()
                }
            }

            if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
                ImageLoadingPlaceHolder(
                    shape = shape,
                    useProgressIndicatorInsteadPlaceHolder = useProgressIndicatorInsteadPlaceHolder,
                )
            } else {
                Box {
                    this@SubcomposeAsyncImage.SubcomposeAsyncImageContent(
                        modifier = Modifier.fillMaxSize(),
                    )
                    cachedOverlay()
                }
            }
        }
    }
}

@Composable
private fun ImageLoadingPlaceHolder(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    useProgressIndicatorInsteadPlaceHolder: Boolean = false,
) {
    Box(
        modifier = modifier
            .let {
                if (!useProgressIndicatorInsteadPlaceHolder) {
                    it.placeholder(
                        visible = true,
                        color = Color(0xFF14171A),
                        shape = shape,
                        highlight = PlaceholderHighlight.fade(),
                    )
                } else {
                    it
                }
            },
    ) {
        if (useProgressIndicatorInsteadPlaceHolder) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}
