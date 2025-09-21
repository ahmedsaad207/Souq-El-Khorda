package com.delighted2wins.souqelkhorda.features.myorders.data.remote

import com.delighted2wins.souqelkhorda.core.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MyOrdersRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : MyOrdersRemoteDataSource {

    private val saleOrdersRef = firestore.collection("orders")
        .document("sale")
        .collection("items")

    private val marketOrdersRef = firestore.collection("orders")
        .document("market")
        .collection("items")

    private fun currentUserId(): String {
        return auth.currentUser?.uid ?: ""
    }

    override suspend fun fetchSaleOrders(): List<Order> {
        val snapshot = saleOrdersRef.get().await()
        return snapshot.documents.mapNotNull { it.toObject(Order::class.java) }
    }

    override suspend fun fetchSells(): List<Order> {
        val myUserId = currentUserId()
        if (myUserId.isEmpty()) return emptyList()

        val snapshot = marketOrdersRef
            .whereEqualTo("userId", myUserId)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(Order::class.java) }
    }

    override suspend fun fetchOffers(): List<Order> {
        val myUserId = currentUserId()
        if (myUserId.isEmpty()) return emptyList()

        val snapshot = marketOrdersRef.get().await()
        return snapshot.documents.mapNotNull { doc ->
            val order = doc.toObject(Order::class.java)
            val offers = doc["offers"] as? List<Map<String, Any>>
            val hasMyOffer = offers?.any { it["userId"] == myUserId } == true
            if (hasMyOffer) order else null
        }
    }
}
