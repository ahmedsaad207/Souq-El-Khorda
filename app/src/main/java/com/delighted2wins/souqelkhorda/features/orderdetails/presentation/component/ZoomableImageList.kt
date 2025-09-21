package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.material3.placeholder
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.shimmer

@Composable
fun ZoomableImageList(urls: List<String>) {
    var selectedImage by remember { mutableStateOf<String?>(null) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(urls) { url ->
            SubcomposeAsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clickable { selectedImage = url }
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .placeholder(
                                    visible = true,
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                    }

                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
        }
    }

    selectedImage?.let { url ->
        Dialog(onDismissRequest = { selectedImage = null }) {
            ZoomableImage(url = url, onClose = { selectedImage = null })
        }
    }
}


@Composable
fun ZoomableImage(url: String, onClose: () -> Unit) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val state = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 4f)
        offsetX += panChange.x
        offsetY += panChange.y
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClose() },
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offsetX,
                    translationY = offsetY
                )
                .transformable(state)
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .placeholder(
                                visible = true,
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                highlight = PlaceholderHighlight.shimmer(),
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
    }
}


