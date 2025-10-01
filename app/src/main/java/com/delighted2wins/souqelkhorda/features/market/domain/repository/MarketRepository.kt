package com.delighted2wins.souqelkhorda.features.market.domain.repository

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser

interface MarketRepository {
    suspend fun getMarketOrders(): List<Order>

    suspend fun fetchUserForMarket(userId: String): MarketUser

}
