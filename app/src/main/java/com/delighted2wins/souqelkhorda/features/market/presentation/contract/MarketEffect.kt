package com.delighted2wins.souqelkhorda.features.market.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser

sealed class MarketEffect {
    data class NavigateToOrderDetails(val order: Order, val user: MarketUser): MarketEffect()
    data class ShowError(val message: String): MarketEffect()
    object NavigateToSellNow: MarketEffect()
}
