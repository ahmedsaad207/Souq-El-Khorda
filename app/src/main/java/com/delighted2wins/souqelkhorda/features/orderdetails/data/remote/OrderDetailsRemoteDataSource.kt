package com.delighted2wins.souqelkhorda.features.orderdetails.data

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderDetailsRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun fetchOrderDetails(orderId: String): ScrapOrder? {
        val doc = firestore.collection("scrap_orders").document(orderId).get().await()
        return doc.toObject(ScrapOrder::class.java)
    }
}

