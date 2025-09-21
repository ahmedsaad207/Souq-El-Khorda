package com.delighted2wins.souqelkhorda.features.orderdetails.data

import android.util.Log
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.model.Order
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

            snapshot.toObject(Order::class.java)

        } catch (e: Exception) {
            Log.e("Order_Details-Debug", "Error fetching market orders:", e)
            null
        }
    }
}

