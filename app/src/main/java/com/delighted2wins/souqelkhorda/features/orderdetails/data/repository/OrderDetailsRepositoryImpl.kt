package com.delighted2wins.souqelkhorda.features.orderdetails.data

import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.repository.OrderDetailsRepository
import javax.inject.Inject

class OrderDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: OrderDetailsRemoteDataSourceImpl
) : OrderDetailsRepository {

    override suspend fun getOrderDetails(orderId: String,source: OrderSource): Order? {
        return remoteDataSource.fetchOrderDetails(orderId, source)
    }
    override suspend fun getOrderDetails(orderId: String, ownerId: String, buyerId: String?, source: OrderSource): Order? {
        return remoteDataSource.fetchOrderDetails(orderId, ownerId, buyerId, source)
    }
}

