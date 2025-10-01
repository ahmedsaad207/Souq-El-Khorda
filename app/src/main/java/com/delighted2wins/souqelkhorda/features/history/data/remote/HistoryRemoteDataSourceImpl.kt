package com.delighted2wins.souqelkhorda.features.history.data.remote

import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.history.data.model.HistoryDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HistoryRemoteDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
): HistoryRemoteDataSource {
    override suspend fun getUserOrders(userId: String): Result<HistoryDto> {
        return try {
            val snapshot = firebaseFirestore
                .collection("history")
                .document(userId)
                .collection("orders")
                .get()
                .await()

            val orders = snapshot.documents.mapNotNull { it.toObject(Order::class.java) }
            Result.success(HistoryDto(orders))
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun addOrder(order: Order): Boolean {
        return try {
            firebaseFirestore
                .collection("history")
                .document(order.userId)
                .collection("orders")
                .document(order.orderId)
                .set(order)
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun addOrderOffer(order: Order, buyerId: String): Boolean {
        return try {
            firebaseFirestore
                .collection("history")
                .document(buyerId)
                .collection("offers")
                .document(order.orderId)
                .set(order)
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun updateOrderStatus(
        orderId: String,
        userId: String,
        orderType: String,
        status: OrderStatus
    ): Boolean {
        return try {
            val orderRef = firebaseFirestore
                .collection("history")
                .document(userId)
                .collection(orderType)
                .document(orderId)

            orderRef.set(mapOf("status" to status.name), SetOptions.merge()).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}