package com.delighted2wins.souqelkhorda.features.market.data.remote

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MarketRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun fetchScrapOrders(): List<ScrapOrder> {
        val snapshot = firestore.collection("scrap_orders").get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(ScrapOrder::class.java)
        }
    }
}
