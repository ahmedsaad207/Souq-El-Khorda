package com.delighted2wins.souqelkhorda.features.myorders.data.remote

interface MyOrdersRemoteDataSource {
    suspend fun fetchSaleOrders(): List<String> // Replace with <Order> data model
    suspend fun fetchOffers(): List<String>    // Replace with <Order> data model
    suspend fun fetchSells(): List<String>    // Replace with <Order> data model
}