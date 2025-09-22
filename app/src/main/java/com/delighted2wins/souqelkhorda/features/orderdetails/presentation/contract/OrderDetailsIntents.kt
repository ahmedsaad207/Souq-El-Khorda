package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

import com.delighted2wins.souqelkhorda.core.enums.OrderSource

sealed class OrderDetailsIntent {
    data class LoadOrderDetails(
        val orderId: String,
        val orderOwnerId: String,
        val buyerId: String? = null,
        val source: OrderSource
    ) : OrderDetailsIntent()

    object Refresh : OrderDetailsIntent()
    object BackClicked : OrderDetailsIntent()
}