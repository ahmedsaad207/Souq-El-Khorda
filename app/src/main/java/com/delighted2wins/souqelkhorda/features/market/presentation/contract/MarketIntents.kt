package com.delighted2wins.souqelkhorda.features.market.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser

sealed class MarketIntent {
    object LoadScrapOrders: MarketIntent()
    object Refresh: MarketIntent()
    data class SearchQueryChanged(val query: String): MarketIntent()
    data class NavigateToOrderDetails(val orderId: String, val orderOwnerId: String ): MarketIntent()
}
