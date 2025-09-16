package com.delighted2wins.souqelkhorda.features.market.data.repository

import com.delighted2wins.souqelkhorda.features.market.data.remote.MarketRemoteDataSource
import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder
import com.delighted2wins.souqelkhorda.features.market.domain.repository.MarketRepository
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val remoteDataSource: MarketRemoteDataSource
) : MarketRepository {

    override suspend fun getScrapOrders(): List<ScrapOrder> {
        return remoteDataSource.fetchScrapOrders()
    }
}
