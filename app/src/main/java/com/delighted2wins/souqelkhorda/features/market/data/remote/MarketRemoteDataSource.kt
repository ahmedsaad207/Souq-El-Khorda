package com.delighted2wins.souqelkhorda.features.market.data.remote

import com.delighted2wins.souqelkhorda.core.model.MainUserDto
import com.delighted2wins.souqelkhorda.core.model.Order

interface MarketRemoteDataSource {
    suspend fun getMarketOrders(): List<Order>
    suspend fun getUser(userId: String): MainUserDto
}