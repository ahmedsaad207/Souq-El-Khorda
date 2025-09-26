package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

sealed class SalesOrderDetailsIntent {
    data class LoadOrderDetails(val orderId: String) : SalesOrderDetailsIntent()
    data class AcceptOffer(val offerId: String) : SalesOrderDetailsIntent()
    data class RejectOffer(val offerId: String) : SalesOrderDetailsIntent()
    data class CancelOffer(val offerId: String) : SalesOrderDetailsIntent()
    data class CompleteOffer(val offerId: String) : SalesOrderDetailsIntent()
    data class ChatWithBuyer(
        val sellerId: String,
        val buyerId: String,
        val orderId: String,
        val offerId: String
    ) : SalesOrderDetailsIntent()
}
