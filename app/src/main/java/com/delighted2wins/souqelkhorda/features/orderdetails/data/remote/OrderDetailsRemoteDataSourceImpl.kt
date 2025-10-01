package com.delighted2wins.souqelkhorda.features.orderdetails.data

import android.annotation.SuppressLint
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.enums.UserRole
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.orderdetails.data.remote.OrderDetailsRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderDetailsRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : OrderDetailsRemoteDataSource {

    @SuppressLint("CheckResult")
    override suspend fun fetchOrderDetails(
        orderId: String,
        source: OrderSource
    ): Order? {
        return fetchOrder(orderId, source)
    }

    private suspend fun fetchOrder(orderId: String, source: OrderSource): Order? {
        return try {
            val covert = if (source == OrderSource.COMPANY) "sale" else "market"

            val snapshot = firestore
                .collection("orders")
                .document(covert)
                .collection("items")
                .document(orderId)
                .get()
                .await()

            val data = snapshot.data ?: return null
            

            val scrapsList: List<Scrap> =
                (data["scraps"] as? List<Map<String, Any>>)?.mapNotNull { scrapMap ->
                    try {
                        val images = when (val img = scrapMap["images"]) {
                            is List<*> -> img.filterIsInstance<String>()
                            is String -> listOf(img)
                            else -> emptyList()
                        }

                        Scrap(
                            id = (scrapMap["id"] as? Double)?.toInt() ?: 0,
                            category = scrapMap["category"] as? String ?: "",
                            unit = scrapMap["unit"] as? String ?: "",
                            amount = scrapMap["amount"]?.toString() ?: "0",
                            description = scrapMap["description"] as? String ?: "",
                            images = images
                        ).also {  }
                    } catch (e: Exception) {
                        
                        null
                    }
                } ?: emptyList()

            Order(
                orderId = snapshot.id,
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
        } catch (e: Exception) {
            
            null
        }
    }
}
