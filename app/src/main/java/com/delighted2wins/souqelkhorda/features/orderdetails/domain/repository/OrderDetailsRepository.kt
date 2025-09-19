package com.delighted2wins.souqelkhorda.features.orderdetails.domain.repository

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder

interface OrderDetailsRepository {
    suspend fun getScrapOrderDetails(orderId: String): ScrapOrder?
}