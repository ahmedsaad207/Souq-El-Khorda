package com.delighted2wins.souqelkhorda.features.orderdetails.data

import com.delighted2wins.souqelkhorda.core.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderDetailsRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun fetchOrderDetails(orderId: String): Order? {
        val doc = firestore.collection("orders").document(orderId).get().await()
        return doc.toObject(Order::class.java)
    }
}

