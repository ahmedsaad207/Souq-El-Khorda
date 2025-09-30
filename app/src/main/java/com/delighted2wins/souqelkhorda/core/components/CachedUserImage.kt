package com.delighted2wins.souqelkhorda.core.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.delighted2wins.souqelkhorda.R
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.placeholder
import com.google.accompanist.placeholder.material3.shimmer

@Composable
fun CachedUserImage(imageUrl: String?, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val loadingState = remember { mutableStateOf(true) }

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
        placeholder = painterResource(R.drawable.user_default),
        error = painterResource(R.drawable.user_default),
        contentDescription = "User profile image",
        onLoading = {
            loadingState.value = true
        },
        onSuccess = {
            loadingState.value = false
        },
        onError = {
            loadingState.value = false
        },
        modifier = modifier
            .placeholder(
                visible = loadingState.value,
                shape = CircleShape,
                highlight = PlaceholderHighlight.shimmer()
            )
    )
}
