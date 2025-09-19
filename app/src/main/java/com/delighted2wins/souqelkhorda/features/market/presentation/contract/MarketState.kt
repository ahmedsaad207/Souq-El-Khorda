package com.delighted2wins.souqelkhorda.features.market.presentation.contract

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder

data class MarketState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val successfulOrders: List<ScrapOrder> = emptyList(),
    val query: String = "",
    val error: String? = null
)
