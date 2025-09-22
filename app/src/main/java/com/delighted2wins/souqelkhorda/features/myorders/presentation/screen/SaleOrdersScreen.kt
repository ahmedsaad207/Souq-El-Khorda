package com.delighted2wins.souqelkhorda.features.myorders.presentation.screen

import androidx.compose.runtime.Composable
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.myorders.presentation.component.OrdersList

@Composable
fun SaleOrdersScreen(
    orders: List<Order>,
    isLoading: Boolean,
    error: String?
) {
    OrdersList(
        orders = orders,
        isLoading = isLoading,
        error = error
    )
}

