package com.delighted2wins.souqelkhorda.features.myorders.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order

sealed class MyOrdersEffect {
    data class ShowSuccess(val message: String): MyOrdersEffect()
    data class ShowError(val message: String): MyOrdersEffect()
}
