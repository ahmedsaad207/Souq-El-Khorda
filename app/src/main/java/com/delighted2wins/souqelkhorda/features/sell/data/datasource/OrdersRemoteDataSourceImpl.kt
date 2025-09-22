package com.delighted2wins.souqelkhorda.features.sell.data.datasource

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.sell.data.remote.firestore.FirestoreOrderService
import javax.inject.Inject

class OrdersRemoteDataSourceImpl @Inject constructor(
    private val service: FirestoreOrderService
) : OrdersRemoteDataSource {
    override suspend fun sendOrder(order: Order) {
        service.sendOrder(order)
    }
}