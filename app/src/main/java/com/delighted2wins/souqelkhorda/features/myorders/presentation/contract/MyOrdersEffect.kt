package com.delighted2wins.souqelkhorda.features.myorders.presentation.contract

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder

sealed class MyOrdersEffect {
    data class NavigateToOrderDetails(val order: ScrapOrder): MyOrdersEffect()
    data class ShowError(val message: String): MyOrdersEffect()
}
