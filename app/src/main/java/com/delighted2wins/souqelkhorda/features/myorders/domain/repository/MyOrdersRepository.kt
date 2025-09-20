package com.delighted2wins.souqelkhorda.features.myorders.domain.repository

interface MyOrdersRepository {
    suspend fun getSaleOrders(): List<String> // Replace with <Order> data model
    suspend fun getOffers(): List<String>    // Replace with <Order> data model
    suspend fun getSells(): List<String>    // Replace with <Order> data model
}
