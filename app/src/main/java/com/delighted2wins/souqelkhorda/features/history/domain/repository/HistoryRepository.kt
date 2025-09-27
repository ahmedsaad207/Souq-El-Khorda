package com.delighted2wins.souqelkhorda.features.history.domain.repository

import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.history.domain.entity.History

interface HistoryRepository {
    suspend fun getUserOrders(): Result<History>
    suspend fun addOrder(order: Order): Boolean
    suspend fun updateOrderStatus(orderId: String,userId: String,orderType: String, status: OrderStatus): Boolean
    suspend fun addOrderOffer(order: Order, buyerId: String): Boolean
}