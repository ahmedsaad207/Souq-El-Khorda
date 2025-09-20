package com.delighted2wins.souqelkhorda.features.sale.data.remote

import com.delighted2wins.souqelkhorda.core.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrdersRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : OrdersRemoteDataSource {
    override suspend fun sendOrder(order: Order) {
        val docRef = firestore
            .collection("orders")
            .document(order.type.name.lowercase())
            .collection("items")
            .add(order.copy())
            .await()

        val orderId = docRef.id

        firestore.collection("orders")
            .document(order.type.name.lowercase())
            .collection("items")
            .document(orderId)
            .update("orderId", orderId)
            .await()
    }
}