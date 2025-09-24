package com.delighted2wins.souqelkhorda.features.sell.data.repo

import com.delighted2wins.souqelkhorda.features.sell.data.datasource.OrdersRemoteDataSource
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remote: OrdersRemoteDataSource
)  : OrderRepository {
    override suspend fun sendOrder(order: Order) {
        remote.sendOrder(order)
    }
    override suspend fun deleteOrder(orderId: String): Boolean {
        return remote.deleteOrder(orderId)
    }

    override suspend fun uploadScrapImages(scraps: List<Scrap>): List<Scrap> {
        return remote.uploadScrapImages(scraps)
    }
}