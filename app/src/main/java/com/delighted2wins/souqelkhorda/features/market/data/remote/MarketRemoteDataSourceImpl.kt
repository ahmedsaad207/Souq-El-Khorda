package com.delighted2wins.souqelkhorda.features.market.data.remote

import android.util.Log
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.enums.UserRole
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
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
                .document(OrderSource.MARKET.toString().lowercase())
                .collection("items")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null

                val scrapsList = (data["scraps"] as? List<Map<String, Any>>)?.map { scrapMap ->
                    Scrap(
                        amount = scrapMap["amount"]?.toString() ?: "",
                        images = emptyList() // TODO
                    )
                } ?: emptyList()

                Order(
                    orderId = doc.id,
                    userId = data["userId"] as? String ?: "",
                    scraps = scrapsList,
                    type = (data["type"] as? String)?.let { OrderType.valueOf(it) } ?: OrderType.SALE,
                    status = (data["status"] as? String)?.let { OrderStatus.valueOf(it) } ?: OrderStatus.PENDING,
                    date = data["date"] as? Long ?: System.currentTimeMillis(),
                    offers = emptyList(),
                    userRole = (data["userRole"] as? String)?.let { UserRole.valueOf(it) } ?: UserRole.SELLER,
                    title = data["title"] as? String ?: "",
                    description = data["description"] as? String ?: "",
                    price = (data["price"] as? Long)?.toInt() ?: 0
                )
            }


        } catch (e: Exception) {
            Log.e("Market-Debug", "Error fetching market orders:", e)
            emptyList()
        }
    }

    override suspend fun getUser(userId: String): MarketUser {
        val snapshot = firestore.collection("users")
            .document(userId)
            .get()
            .await()

        if (!snapshot.exists()) throw Exception("User not found")

        val id = snapshot.getString("id") ?: ""
        val name = snapshot.getString("name") ?: ""
        val governorate = snapshot.getString("governorate") ?: ""
        val address = snapshot.getString("address") ?: ""
        val userImage = snapshot.getString("userImage")

        return MarketUser(
            id = id,
            name = name,
            location = "$governorate, $address",
            imageUrl = userImage
        )
    }

}
