package com.delighted2wins.souqelkhorda.features.myorders.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order

sealed class MyOrdersEffect {
    data class NavigateToOrderDetails(val order: Order): MyOrdersEffect()
    data class ShowError(val message: String): MyOrdersEffect()
}
