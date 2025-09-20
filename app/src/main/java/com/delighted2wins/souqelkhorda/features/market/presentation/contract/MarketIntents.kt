package com.delighted2wins.souqelkhorda.features.market.presentation.contract

import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Order

sealed class MarketIntent {
    object LoadScrapOrders: MarketIntent()
    object Refresh: MarketIntent()
    data class SearchQueryChanged(val query: String): MarketIntent()
    data class ClickOrder(val order: Order): MarketIntent()
    object SellNowClicked: MarketIntent()
}
