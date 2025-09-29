package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.model.Order

@Composable
fun OrderSummaryCard(order: Order) {
    val context = LocalContext.current

    val cardGradient = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
        )
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = cardGradient),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SummaryItem(
                label = stringResource(R.string.total_price),
                value = context.getString(R.string.price, order.price),
                icon = Icons.Default.AttachMoney
            )
            val totalWeight = order.scraps.sumOf { scrap ->
                scrap.amount.toIntOrNull() ?: 0
            }.toString()

            SummaryItem(
                label = stringResource(R.string.total_weight),
                value = context.getString(R.string.weight_label, totalWeight),
                icon = Icons.Default.Scale
            )
            SummaryItem(
                label = stringResource(R.string.total_items),
                value = order.scraps.size.toString(),
                icon = Icons.Default.Inventory2
            )
        }
    }
}

