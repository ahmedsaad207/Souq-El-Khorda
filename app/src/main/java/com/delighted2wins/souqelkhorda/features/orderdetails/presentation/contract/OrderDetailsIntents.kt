package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder

sealed class OrderDetailsIntent {
    data class LoadOrderDetails(val order: ScrapOrder) : OrderDetailsIntent()
    object Retry : OrderDetailsIntent()
    object BackClicked : OrderDetailsIntent()
}