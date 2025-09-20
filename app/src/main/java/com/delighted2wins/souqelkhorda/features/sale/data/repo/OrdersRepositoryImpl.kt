package com.delighted2wins.souqelkhorda.features.sale.data.repo

import com.delighted2wins.souqelkhorda.features.sale.data.remote.OrdersRemoteDataSource
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.sale.domain.repo.OrdersRepository
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val remote: OrdersRemoteDataSource
)  : OrdersRepository {
    override suspend fun sendOrder(order: Order) {
        remote.sendOrder(order)
    }
}