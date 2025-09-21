package com.delighted2wins.souqelkhorda.features.sell.data.repo

import com.delighted2wins.souqelkhorda.features.sell.data.remote.OrdersRemoteDataSource
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.OrdersRepository
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val remote: OrdersRemoteDataSource
)  : OrdersRepository {
    override suspend fun sendOrder(order: Order) {
        remote.sendOrder(order)
    }
}