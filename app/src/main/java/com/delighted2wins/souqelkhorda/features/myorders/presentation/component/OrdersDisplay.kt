package com.delighted2wins.souqelkhorda.features.myorders.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.core.components.EmptyCart
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.myorders.presentation.screen.CompanyOrderCard

@Composable
fun OrdersDisplay(
    orders: List<Order>,
    isLoading: Boolean,
    error: String?,
    onDetailsClick: (String, String) -> Unit,
    onCancelClick: (String) -> Unit
) {
    val systemIsRtl = LocalConfiguration.current.layoutDirection == LayoutDirection.Rtl.ordinal
    when {
        isLoading -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(20) {
                    ShimmerOrderItem()
                }
            }
        }

        error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                EmptyCart(messageInfo = error)
            }
        }

        orders.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
               EmptyCart(messageInfo = stringResource(R.string.no_orders_available))
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(orders) { order ->
//                    OrderItem(
//                        order = order,
//                        onDetailsClick = onDetailsClick,
//                    )
                    CompanyOrderCard(
                        order = order,
                        onDetailsClick = onDetailsClick,
                        onCancelClick = onCancelClick,
                        systemIsRtl = systemIsRtl
                    )
                }
            }
        }
    }
}