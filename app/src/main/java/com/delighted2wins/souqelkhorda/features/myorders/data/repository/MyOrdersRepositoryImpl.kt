package com.delighted2wins.souqelkhorda.features.myorders.data.repository

import com.delighted2wins.souqelkhorda.features.myorders.data.remote.MyOrdersRemoteDataSource
import com.delighted2wins.souqelkhorda.features.myorders.domain.repository.MyOrdersRepository
import javax.inject.Inject

class MyOrdersRepositoryImpl @Inject constructor(
    private val remoteDataSource: MyOrdersRemoteDataSource
): MyOrdersRepository {
    override suspend fun getSaleOrders(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getOffers(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getSells(): List<String> {
        TODO("Not yet implemented")
    }
}