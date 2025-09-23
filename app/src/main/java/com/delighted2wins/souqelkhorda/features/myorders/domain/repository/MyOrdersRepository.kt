package com.delighted2wins.souqelkhorda.features.myorders.domain.repository

import com.delighted2wins.souqelkhorda.core.model.Order

interface MyOrdersRepository {
    suspend fun getCompanyOrders(): List<Order>
    suspend fun getOffers(): List<Order>
    suspend fun getSells(): List<Order>
}
