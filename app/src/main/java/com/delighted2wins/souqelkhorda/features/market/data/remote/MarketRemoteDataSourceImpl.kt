package com.delighted2wins.souqelkhorda.features.market.data.remote

import android.util.Log
import com.delighted2wins.souqelkhorda.core.model.MainUserDto
import com.delighted2wins.souqelkhorda.core.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MarketRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): MarketRemoteDataSource {
    override suspend fun getMarketOrders(): List<Order> {
        return try {
            val snapshot = firestore
                .collection("orders")
                .document("market")
                .collection("items")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc -> doc.toObject(Order::class.java) }

        } catch (e: Exception) {
            Log.e("Market-Debug", "Error fetching market orders:", e)
            emptyList()
        }
    }

    override suspend fun getUser(userId: String): MainUserDto {
        val snapshot = firestore.collection("users")
            .document(userId)
            .get()
            .await()

        return snapshot.toObject(MainUserDto::class.java)
            ?: throw Exception("User not found")
    }

}
