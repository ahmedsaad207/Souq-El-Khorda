package com.delighted2wins.souqelkhorda.features.market.data.repository

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.data.mapper.toMarketUser
import com.delighted2wins.souqelkhorda.features.market.data.remote.MarketRemoteDataSource
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.domain.repository.MarketRepository
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val remoteDataSource: MarketRemoteDataSource
) : MarketRepository {

    override suspend fun getMarketOrders(): List<Order> {
        return remoteDataSource.getMarketOrders()
    }

    override suspend fun fetchUserForMarket(userId: String): MarketUser {
        return remoteDataSource.getUser(userId)
    }

}
