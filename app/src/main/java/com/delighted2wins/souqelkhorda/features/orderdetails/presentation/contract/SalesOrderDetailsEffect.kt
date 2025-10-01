package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

sealed class SalesOrderDetailsEffect {
    data class NavigateToChat(
        val orderId: String,
        val sellerId: String,
        val buyerId: String,
        val offerId: String
    ) : SalesOrderDetailsEffect()

    data class ShowError(val message: String) : SalesOrderDetailsEffect()

    data class ShowSuccess(val message: String) : SalesOrderDetailsEffect()
}