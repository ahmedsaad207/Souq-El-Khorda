package com.delighted2wins.souqelkhorda.features.market.presentation.component.OrderDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.CachedUserImage
import kotlinx.coroutines.delay

@Composable
fun ProductImageSection(
    imageUrls: List<String>,
    onBackClick: () -> Unit = {},
) {
    var currentImageIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = imageUrls) {
        if (imageUrls.size > 1) {
            while (true) {
                delay(3000)
                currentImageIndex = (currentImageIndex + 1) % imageUrls.size
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        CachedUserImage(
            imageUrl = imageUrls[currentImageIndex],
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onBackClick() }
            )

            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            imageUrls.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (index == currentImageIndex) Color.White else Color.Gray)
                )
            }
        }
    }
}