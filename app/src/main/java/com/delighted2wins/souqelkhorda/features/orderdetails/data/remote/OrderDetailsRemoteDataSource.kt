package com.delighted2wins.souqelkhorda.features.orderdetails.data.remote

import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.model.Order

interface OrderDetailsRemoteDataSource {
    suspend fun fetchOrderDetails(orderId: String, source: OrderSource): Order?
}