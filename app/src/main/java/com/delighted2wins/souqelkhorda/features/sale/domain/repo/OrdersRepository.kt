package com.delighted2wins.souqelkhorda.features.sale.domain.repo

import com.delighted2wins.souqelkhorda.core.model.Order

interface OrdersRepository {
    suspend fun sendOrder(order: Order)
}