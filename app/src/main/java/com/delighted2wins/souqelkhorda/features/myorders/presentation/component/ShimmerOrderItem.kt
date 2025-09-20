package com.delighted2wins.souqelkhorda.features.myorders.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.placeholder
import com.google.accompanist.placeholder.material3.shimmer

@Composable
fun ShimmerOrderItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.LightGray.copy(alpha = 0.3f)
            )
    )
}