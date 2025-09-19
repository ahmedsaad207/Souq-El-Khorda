package com.delighted2wins.souqelkhorda.features.market.presentation.contract

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder

sealed class MarketIntent {
    object LoadScrapOrders: MarketIntent()
    data class SearchQueryChanged(val query: String): MarketIntent()
    data class ClickOrder(val order: ScrapOrder): MarketIntent()
    object SellNowClicked: MarketIntent()
}
