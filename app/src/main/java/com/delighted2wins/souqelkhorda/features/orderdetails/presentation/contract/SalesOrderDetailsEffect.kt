package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

sealed class SalesOrderDetailsEffect {
    object NavigateBack : OrderDetailsEffect()

    data class NavigateToChat(
        val sellerId: String,
        val buyerId: String,
        val orderId: String
    ) : SalesOrderDetailsEffect()

    data class ShowError(val message: String) : OrderDetailsEffect()
}