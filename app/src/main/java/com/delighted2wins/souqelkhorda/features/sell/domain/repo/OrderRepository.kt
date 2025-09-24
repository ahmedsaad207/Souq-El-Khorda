package com.delighted2wins.souqelkhorda.features.sell.domain.repo

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap

interface OrderRepository {
    suspend fun sendOrder(order: Order)
    suspend fun deleteOrder(orderId: String): Boolean
    suspend fun uploadScrapImages(scraps: List<Scrap>): List<Scrap>
}