package com.delighted2wins.souqelkhorda.features.offers.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.core.model.Offer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OfferCard(
    offer: Offer,
    buyerName: String? = null,
    isRtl: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val statusColor = when (offer.status) {
        OfferStatus.PENDING -> Color.Yellow
        OfferStatus.ACCEPTED -> Color.Green
        OfferStatus.REJECTED -> Color.Red
    }

    val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(offer.date))

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DirectionalText(
                    text = buyerName ?: offer.buyerId,
                    contentIsRtl = isRtl,
                    style = MaterialTheme.typography.titleMedium
                )

                Box(
                    modifier = Modifier
                        .background(statusColor, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = offer.status.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            DirectionalText(
                text = "${offer.offerPrice}"+ if (isRtl) "جنيه" else "EGP",
                contentIsRtl = isRtl,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            DirectionalText(
                text = formattedDate,
                contentIsRtl = isRtl,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}
