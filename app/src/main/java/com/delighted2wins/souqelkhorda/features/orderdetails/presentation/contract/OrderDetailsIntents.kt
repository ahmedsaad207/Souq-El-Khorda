package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

import com.delighted2wins.souqelkhorda.core.model.Order

sealed class OrderDetailsIntent {
    data class LoadOrderDetails(val order: Order) : OrderDetailsIntent()
    object Retry : OrderDetailsIntent()
    object BackClicked : OrderDetailsIntent()
}