package com.delighted2wins.souqelkhorda.features.myorders.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order

data class MyOrdersState(
    val isLoading: Boolean = false,
    val saleOrders: List<Order> = emptyList(),
    val offers: List<Order> = emptyList(),
    val sells: List<Order> = emptyList(),
    val error: String? = null,
    val currentBuyerId: String? = null
) {
    val saleCount: Int get() = saleOrders.size
    val offersCount: Int get() = offers.size
    val sellsCount: Int get() = sells.size
}
