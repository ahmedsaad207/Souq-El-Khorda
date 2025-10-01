package com.delighted2wins.souqelkhorda.features.myorders.data.repository

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.myorders.data.remote.MyOrdersRemoteDataSource
import com.delighted2wins.souqelkhorda.features.myorders.domain.repository.MyOrdersRepository
import javax.inject.Inject

class MyOrdersRepositoryImpl @Inject constructor(
    private val remoteDataSource: MyOrdersRemoteDataSource
): MyOrdersRepository {
    override suspend fun getCompanyOrders(): List<Order> {
        return remoteDataSource.fetchCompanyOrders()
    }

    override suspend fun getOffers(): List<Order> {
        return remoteDataSource.fetchOffers()
    }

    override suspend fun getSells(): List<Order> {
        return remoteDataSource.fetchSells()
    }
}