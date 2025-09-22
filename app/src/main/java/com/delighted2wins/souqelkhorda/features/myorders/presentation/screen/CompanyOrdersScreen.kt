package com.delighted2wins.souqelkhorda.features.myorders.presentation.screen

import ShimmerOrderCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.core.model.Order

@Composable
fun CompanyOrdersScreen(
    orders: List<Order>,
    isLoading: Boolean,
    error: String?,
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
                Text(
                    text = error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(orders) { order ->
                        CompanyOrderCard(
                            order = order,
                            onClick = {
                            },
                            onDetailsClick = { orderId, userId ->
                            },
                            onDeclineClick = {
                            },
                            systemIsRtl = systemIsRtl
                        )
                    }
                }
            }
        }
    }
}
