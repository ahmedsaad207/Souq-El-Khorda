package com.delighted2wins.souqelkhorda.features.market.data.repository

import com.delighted2wins.souqelkhorda.features.market.data.remote.MarketRemoteDataSource
import com.delighted2wins.souqelkhorda.features.market.domain.repository.MarketRepository
import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Order
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val remoteDataSource: MarketRemoteDataSource
) : MarketRepository {

    override suspend fun getMarketOrders(): List<Order> {
        return remoteDataSource.getMarketOrders()
    }

}
