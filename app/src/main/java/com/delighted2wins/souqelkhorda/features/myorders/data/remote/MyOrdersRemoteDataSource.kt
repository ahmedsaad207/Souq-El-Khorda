package com.delighted2wins.souqelkhorda.features.myorders.data.remote

import com.delighted2wins.souqelkhorda.core.model.Order

interface MyOrdersRemoteDataSource {
    suspend fun fetchSaleOrders(): List<Order>
    suspend fun fetchOffers(): List<Order>
    suspend fun fetchSells(): List<Order>
}