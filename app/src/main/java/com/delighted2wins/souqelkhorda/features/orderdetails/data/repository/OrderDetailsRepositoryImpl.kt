package com.delighted2wins.souqelkhorda.features.orderdetails.data

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder
import com.delighted2wins.souqelkhorda.features.orderdetails.data.remote.OrderDetailsRemoteDataSource
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.repository.OrderDetailsRepository
import javax.inject.Inject

class OrderDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: OrderDetailsRemoteDataSource
) : OrderDetailsRepository {

    override suspend fun getScrapOrderDetails(orderId: String): ScrapOrder? {
        return remoteDataSource.fetchOrderDetails(orderId)
    }
}

