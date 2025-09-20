package com.delighted2wins.souqelkhorda.features.market.data.remote

import android.util.Log
import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Order
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MarketRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getMarketOrders(): List<Order> {
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

}
