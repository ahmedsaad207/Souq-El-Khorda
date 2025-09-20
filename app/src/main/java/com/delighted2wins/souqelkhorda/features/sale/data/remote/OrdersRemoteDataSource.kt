package com.delighted2wins.souqelkhorda.features.sale.data.remote

import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Order
import kotlinx.coroutines.flow.Flow

interface OrdersRemoteDataSource {

    suspend fun sendOrder(order: Order)
}