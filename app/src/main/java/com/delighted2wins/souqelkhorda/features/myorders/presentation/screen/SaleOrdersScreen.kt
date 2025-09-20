package com.delighted2wins.souqelkhorda.features.myorders.presentation.screen

import androidx.compose.runtime.Composable
import com.delighted2wins.souqelkhorda.features.myorders.presentation.component.OrdersList

@Composable
fun SaleOrdersScreen(
    orders: List<String>,
    isLoading: Boolean,
    error: String?
) {
    OrdersList(
        orders = orders,
        isLoading = isLoading,
        error = error
    )
}

