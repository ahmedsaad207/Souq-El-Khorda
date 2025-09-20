package com.delighted2wins.souqelkhorda.features.market.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order

sealed class MarketEffect {
    data class NavigateToOrderDetails(val order: Order): MarketEffect()
    data class ShowError(val message: String): MarketEffect()
    object NavigateToSellNow: MarketEffect()
}
