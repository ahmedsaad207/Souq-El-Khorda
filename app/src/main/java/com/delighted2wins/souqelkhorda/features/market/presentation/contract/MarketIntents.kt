package com.delighted2wins.souqelkhorda.features.market.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Offer

sealed class MarketIntent {
    object LoadScrapOrders: MarketIntent()
    object Refresh: MarketIntent()
    data class MakeOffer(val offer: Offer, val sellerId: String): MarketIntent()
    data class SearchQueryChanged(val query: String): MarketIntent()
    data class NavigateToOrderDetails(val orderId: String, val orderOwnerId: String ): MarketIntent()
}
