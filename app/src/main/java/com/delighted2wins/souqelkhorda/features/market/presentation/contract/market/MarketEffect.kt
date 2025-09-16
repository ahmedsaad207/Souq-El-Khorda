package com.delighted2wins.souqelkhorda.features.market.presentation.contract.market

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder

sealed class MarketEffect {
    data class NavigateToOrderDetails(val order: ScrapOrder): MarketEffect()
    data class ShowError(val message: String): MarketEffect()
}
