package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus

@Composable
fun StatusChip(status: String) {
    Surface(
        shape = RoundedCornerShape(50),
        tonalElevation = 2.dp,
        shadowElevation = 0.dp,
        color = when (status) {
            OrderStatus.PENDING.toString() -> OrderStatus.PENDING.color
            OrderStatus.COMPLETED.toString() -> OrderStatus.COMPLETED.color
            OrderStatus.CANCELLED.toString() -> OrderStatus.CANCELLED.color
            else -> MaterialTheme.colorScheme.primary
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = when (status) {
                    OrderStatus.PENDING.toString() -> Icons.Default.Schedule
                    OrderStatus.COMPLETED.toString() -> Icons.Default.CheckCircle
                    OrderStatus.CANCELLED.toString() -> Icons.Default.Close
                    else -> Icons.Default.Schedule
                },
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = status,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}