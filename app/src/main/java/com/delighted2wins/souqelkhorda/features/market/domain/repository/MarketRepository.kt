package com.delighted2wins.souqelkhorda.features.market.domain.repository

import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Order

interface MarketRepository {
    suspend fun getMarketOrders(): List<Order>

    suspend fun fetchUserForMarket(userId: String): MarketUser

}
