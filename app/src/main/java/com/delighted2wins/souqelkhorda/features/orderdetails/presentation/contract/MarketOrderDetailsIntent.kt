package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.model.Order

sealed class MarketOrderDetailsIntent {
    data class LoadOrder(val orderId: String, val ownerId: String) : MarketOrderDetailsIntent()
    data class MakeOffer(val order: Order, val offer: Offer, val sellerId: String) : MarketOrderDetailsIntent()
    object Refresh : MarketOrderDetailsIntent()
}