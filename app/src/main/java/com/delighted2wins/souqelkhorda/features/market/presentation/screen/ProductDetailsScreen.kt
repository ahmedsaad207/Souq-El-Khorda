package com.delighted2wins.souqelkhorda.features.market.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.features.market.presentation.component.*

@Composable
fun ProductDetailsScreen(
    productId: Int,
    onBackClick: () -> Unit = {}
) {
    val imageUrls = listOf(
        "https://images.unsplash.com/photo-1542393359-994b1509a25b?q=80&w=2670&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1517336714731-489689fd1e86?q=80&w=2574&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1611095790444-16a73c1503e7?q=80&w=2670&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1611095790444-16a73c1503e7?q=80&w=2670&auto=format&fit=crop",
        "https://images.unsplash.com/photo-1517336714731-489689fd1e86?q=80&w=2574&auto=format&fit=crop"
    )

    Surface(
        color = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            ProductImageSection(
                imageUrls = imageUrls,
                onBackClick = { onBackClick() }
            )
            ProductInfoSection()
            DescriptionSection()
            SellerInfoSection(
                userImage = "https://avatar.iran.liara.run/public/boy?username=Scott",
                userName = "John Doe",
                isVerified = true,
                rating = 4.8,
                reviewCount = 120
            )
            Spacer(modifier = Modifier.width(8.dp))
            ActionButtonsSection()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductDetailsScreenPreview() {
    ProductDetailsScreen(0)
}