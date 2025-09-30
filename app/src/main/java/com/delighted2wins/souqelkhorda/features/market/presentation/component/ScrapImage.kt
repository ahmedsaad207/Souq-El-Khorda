package com.delighted2wins.souqelkhorda.features.market.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.placeholder
import com.google.accompanist.placeholder.material3.shimmer

@Composable
fun ScrapImage(scrap: Scrap) {
    val url = scrap.images.firstOrNull() ?: ""

    val painter = rememberAsyncImagePainter(
        model = if (url.isNotEmpty()) {
            ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build()
        } else null,
        placeholder = painterResource(R.drawable.image_not_found),
        error = painterResource(R.drawable.image_not_found)
    )

    val isLoading = painter.state is AsyncImagePainter.State.Loading

    Image(
        painter = painter,
        contentDescription = "Scrap Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .placeholder(
                visible = isLoading,
                shape = RoundedCornerShape(8.dp),
                highlight = PlaceholderHighlight.shimmer()
            )
    )
}
