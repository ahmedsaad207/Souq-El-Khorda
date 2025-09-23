package com.delighted2wins.souqelkhorda.features.market.data.remote

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser

interface MarketRemoteDataSource {
    suspend fun getMarketOrders(): List<Order>
    suspend fun getUser(userId: String): MarketUser
}