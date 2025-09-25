package com.delighted2wins.souqelkhorda.features.myorders.presentation.screen

import ShimmerOrderCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.components.DirectionalText
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.model.Order

@Composable
fun CompanyOrdersScreen(
    orders: List<Order>,
    isLoading: Boolean,
    error: String?,
    onDetailsClick: (String, String) -> Unit,
    onDeclineClick: (String) -> Unit,
    systemIsRtl: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(10) {
                        ShimmerOrderCard()
                    }
                }
            }
            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DirectionalText(
                        text = error,
                        contentIsRtl = systemIsRtl,
                        style = typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(orders) { order ->
                        CompanyOrderCard(
                            order = order,
                            onDetailsClick = { orderId, userId, -> onDetailsClick(orderId, userId) },
                            onDeclineClick = { orderId -> onDeclineClick(orderId) },
                            systemIsRtl = systemIsRtl
                        )
                    }
                }
            }
        }
    }
}
