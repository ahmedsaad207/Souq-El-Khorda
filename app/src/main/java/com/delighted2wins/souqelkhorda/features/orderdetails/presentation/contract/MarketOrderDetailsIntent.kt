package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

sealed class MarketOrderDetailsIntent {
    data class LoadOrder(val orderId: String, val ownerId: String) : MarketOrderDetailsIntent()
    object Refresh : MarketOrderDetailsIntent()
}