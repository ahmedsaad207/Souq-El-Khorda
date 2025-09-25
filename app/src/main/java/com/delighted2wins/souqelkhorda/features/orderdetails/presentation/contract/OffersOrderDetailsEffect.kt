package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

sealed class OffersOrderDetailsEffect {
    data class NavigateToChat(val orderId: String, val sellerId: String, val buyerId: String) : OffersOrderDetailsEffect()
    data class ShowSuccess(val message: String) : OffersOrderDetailsEffect()
    data class ShowError(val message: String) : OffersOrderDetailsEffect()
}
