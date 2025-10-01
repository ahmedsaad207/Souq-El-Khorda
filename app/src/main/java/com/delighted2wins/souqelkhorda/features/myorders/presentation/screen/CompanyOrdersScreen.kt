package com.delighted2wins.souqelkhorda.features.myorders.presentation.screen

import ShimmerOrderCard
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.EmptyCart
import com.delighted2wins.souqelkhorda.core.model.Order

@Composable
fun CompanyOrdersScreen(
    orders: List<Order>,
    isLoading: Boolean,
    error: String?,
    onCompanyDetailsClick: (String, String) -> Unit,
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
                EmptyCart(
                    messageInfo = error,
                )
            }

            orders.isEmpty() -> {
                EmptyCart(
                    messageInfo = stringResource(R.string.no_orders_found),
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
                            onDetailsClick = { orderId, userId, -> onCompanyDetailsClick(orderId, userId) },
                            onCancelClick = { orderId -> onDeclineClick(orderId) },
                            systemIsRtl = systemIsRtl
                        )
                    }
                }
            }
        }
    }
}
