package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

sealed class OrderDetailsEffect {
    object NavigateBack : OrderDetailsEffect()
    data class ShowError(val message: String) : OrderDetailsEffect()
}