package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser

@Composable
fun BuyerOfferCard(
    offer: Offer,
    seller: MarketUser,
    onChat: () -> Unit,
    onMarkReceived: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Sell,
                    contentDescription = "Offer Price",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "${offer.offerPrice} EGP",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    ),
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = offer.status.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = when (offer.status) {
                            OfferStatus.PENDING -> Color.Red
                            OfferStatus.ACCEPTED -> Color.Green
                            else -> Color.Gray
                        }
                    ),
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                when (offer.status) {
                    OfferStatus.PENDING -> {
                        OutlinedButton(
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = onChat,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Update Offer")
                        }
                    }

                    OfferStatus.ACCEPTED -> {
                        Button(
                            onClick = onMarkReceived,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Chat")
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Received")
                        }
                        OutlinedButton(
                            onClick = onCancel,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }
                    }

                    else -> {
                        Text(
                            text = "No actions available",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
