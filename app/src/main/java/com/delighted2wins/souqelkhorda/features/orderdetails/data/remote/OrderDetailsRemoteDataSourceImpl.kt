package com.delighted2wins.souqelkhorda.features.orderdetails.data

import android.util.Log
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
): OrderDetailsRemoteDataSource {

    override suspend fun fetchOrderDetails(
        orderId: String,
        ownerId: String,
        buyerId: String?,
        source: OrderSource
    ): Order? {
        return try {
            val snapshot = firestore
                .collection("orders")
                .document(source.toString().lowercase())
                .collection("items")
                .document(orderId)
                .get()
                .await()

            val data = snapshot.data ?: return null

            val scrapsList = (data["scraps"] as? List<Map<String, Any>>)?.map { scrapMap ->
                Scrap(
                    amount = scrapMap["amount"]?.toString() ?: ""
                )
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
            Log.e("Order_Details-Debug", "Error fetching order details:", e)
            null
        }
    }
}
