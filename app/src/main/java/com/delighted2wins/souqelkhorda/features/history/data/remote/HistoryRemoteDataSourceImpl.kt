package com.delighted2wins.souqelkhorda.features.history.data.remote

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.history.data.model.HistoryDto
import com.google.firebase.firestore.FirebaseFirestore
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
}