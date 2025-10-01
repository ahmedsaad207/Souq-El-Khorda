package com.delighted2wins.souqelkhorda.features.history.data.remote

import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.history.data.model.HistoryDto

interface HistoryRemoteDataSource {
    suspend fun getUserOrders(userId: String): Result<HistoryDto>
    suspend fun addOrder(order: Order): Boolean
    suspend fun updateOrderStatus(orderId: String, userId: String,orderType: String, status: OrderStatus): Boolean
    suspend fun addOrderOffer(order: Order, buyerId: String): Boolean
}