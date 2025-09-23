package com.delighted2wins.souqelkhorda.features.sell.data.remote.firestore

import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreOrderService @Inject constructor(
    val firestore: FirebaseFirestore
) {

    suspend fun sendOrder(order: Order) {
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

        firestore.collection("history")
            .add(order)
    }

    suspend fun deleteCompanyOrder(orderId: String): Boolean {
        return try {
            firestore.collection("orders")
                .document(OrderType.SALE.name.lowercase())
                .collection("items")
                .document(orderId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}