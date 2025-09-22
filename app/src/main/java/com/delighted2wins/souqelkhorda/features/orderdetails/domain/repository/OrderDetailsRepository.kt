package com.delighted2wins.souqelkhorda.features.orderdetails.domain.repository

import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.model.Order

interface OrderDetailsRepository {
    suspend fun getOrderDetails(orderId: String, ownerId: String, buyerId: String?, source: OrderSource): Order?
}