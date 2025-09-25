package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.model.Offer

@Composable
fun OfferItem(
    offer: Offer,
    orderStatus: OrderStatus,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (offer.status) {
                OfferStatus.ACCEPTED -> Color(0xFFD4EDDA)
                OfferStatus.REJECTED -> Color(0xFFF8D7DA)
                OfferStatus.PENDING -> Color.White
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Buyer: ${offer.buyerId}", fontWeight = FontWeight.Bold)
                Text("Offer: ${offer.offerPrice}")
                Text("Status: ${offer.status}")
            }

            if (orderStatus != OrderStatus.COMPLETED) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (offer.status == OfferStatus.PENDING) {
                        Button(onClick = onAccept, colors = ButtonDefaults.buttonColors(Color.Green)) {
                            Text("Accept", color = Color.White)
                        }
                        Button(onClick = onReject, colors = ButtonDefaults.buttonColors(Color.Red)) {
                            Text("Reject", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
