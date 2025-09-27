package com.delighted2wins.souqelkhorda.features.sell.data.datasource

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap

interface OrdersRemoteDataSource {

    suspend fun sendOrder(order: Order)
    suspend fun deleteCompanyOrder(orderId: String): Boolean
    suspend fun deleteMarketOrder(orderId: String): Boolean

    suspend fun uploadScrapImages(scraps: List<Scrap>): List<Scrap>
}