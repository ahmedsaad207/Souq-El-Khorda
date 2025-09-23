package com.delighted2wins.souqelkhorda.features.sell.data.datasource

import com.delighted2wins.souqelkhorda.core.model.Order

interface OrdersRemoteDataSource {

    suspend fun sendOrder(order: Order)
    suspend fun deleteOrder(orderId: String): Boolean
}