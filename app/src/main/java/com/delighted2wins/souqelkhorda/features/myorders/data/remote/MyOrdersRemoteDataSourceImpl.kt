package com.delighted2wins.souqelkhorda.features.myorders.data.remote

import com.google.firebase.firestore.FirebaseFirestore

class MyOrdersRemoteDataSourceImpl(
    private val  firestore: FirebaseFirestore
): MyOrdersRemoteDataSource  {
    override suspend fun fetchSaleOrders(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchOffers(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSells(): List<String> {
        TODO("Not yet implemented")
    }
}