package com.delighted2wins.souqelkhorda.features.sell.domain.repo

import com.delighted2wins.souqelkhorda.core.model.Order

interface OrderRepository {
    suspend fun sendOrder(order: Order)
    suspend fun deleteOrder(orderId: String): Boolean
}