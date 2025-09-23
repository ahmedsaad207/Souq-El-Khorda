package com.delighted2wins.souqelkhorda.features.market.presentation.contract

sealed class MarketEffect {
    data class NavigateToOrderDetails(val orderId: String, val ownerId: String): MarketEffect()
    data class ShowError(val message: String): MarketEffect()

    data class ShowSuccess(val message: String): MarketEffect()
}
