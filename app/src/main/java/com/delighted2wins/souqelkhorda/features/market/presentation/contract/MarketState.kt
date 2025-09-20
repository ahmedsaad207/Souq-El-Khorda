package com.delighted2wins.souqelkhorda.features.market.presentation.contract

import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Order

data class MarketState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val successfulOrders: List<Order> = emptyList(),
    val query: String = "",
    val error: String? = null,
    val isEmpty: Boolean = false
)
