package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

sealed class OffersOrderDetailsIntent {
    data class LoadOrderDetails(val orderId: String) : OffersOrderDetailsIntent()
    data class ChatWithSeller(val orderId: String, val sellerId: String, val buyerId: String, val offerId: String) : OffersOrderDetailsIntent()
    data class CancelOffer(val offerId: String, val sellerId: String) : OffersOrderDetailsIntent()
    data class UpdateOffer(val offerId: String, val newPrice: String, val sellerId: String) : OffersOrderDetailsIntent()
    data class MarkAsReceived(val offerId: String, val sellerId: String) : OffersOrderDetailsIntent()
}
