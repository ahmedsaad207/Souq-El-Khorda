package com.delighted2wins.souqelkhorda.features.sale.data.remote

import com.delighted2wins.souqelkhorda.core.model.Order

interface OrdersRemoteDataSource {

    suspend fun sendOrder(order: Order)
}