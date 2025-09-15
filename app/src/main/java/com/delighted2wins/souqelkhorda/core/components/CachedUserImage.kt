package com.delighted2wins.souqelkhorda.core.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.delighted2wins.souqelkhorda.R

@Composable
fun CachedUserImage(imageUrl: String?, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    AsyncImage(
        model = if (!imageUrl.isNullOrEmpty()) {
            ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .size(Size.ORIGINAL)
                .build()
        } else null,
        placeholder = painterResource(R.drawable.outline_person_24),
        error = painterResource(R.drawable.outline_person_24),
        contentDescription = "User profile image",
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
    )
}
