package com.delighted2wins.souqelkhorda.features.market.domain.repository

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder

interface MarketRepository {
    suspend fun getScrapOrders(): List<ScrapOrder>
}
