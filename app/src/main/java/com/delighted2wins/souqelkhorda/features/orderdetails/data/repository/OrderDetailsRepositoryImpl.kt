package com.delighted2wins.souqelkhorda.features.orderdetails.data

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.repository.OrderDetailsRepository
import javax.inject.Inject

class OrderDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: OrderDetailsRemoteDataSource
) : OrderDetailsRepository {

    override suspend fun getScrapOrderDetails(orderId: String): Order? {
        return remoteDataSource.fetchOrderDetails(orderId)
    }
}

