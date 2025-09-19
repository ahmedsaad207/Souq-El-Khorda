package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrderItem

@Composable
fun OrderItemCard(
    item: ScrapOrderItem,
    contentIsRtl: Boolean = false,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth().padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DirectionalText(
                text = item.name,
                contentIsRtl = contentIsRtl,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DirectionalText(
                    text = if (contentIsRtl) "الوزن: ${item.weight} كجم" else "Weight: ${item.weight} Kg",
                    contentIsRtl = contentIsRtl,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )

                item.quantity?.let {
                    DirectionalText(
                        text = if (contentIsRtl) "العدد: $it" else "Quantity: $it",
                        contentIsRtl = contentIsRtl,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(2f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))


            if (item.images.isNullOrEmpty()) {
                Text(
                    text = if (contentIsRtl) "لا توجد صور" else "No images available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            } else {
                ZoomableImageList(urls = item.images)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrderItemCardPreview() {
    val sampleItem = ScrapOrderItem(
        id = 1,
        name = "Sample Scrap Item",
        weight = 15,
        quantity = 3,
        images = listOf(
            "https://via.placeholder.com/150",
            "https://via.placeholder.com/200"
        )
    )

    OrderItemCard(item = sampleItem, contentIsRtl = false)
}