package com.delighted2wins.souqelkhorda.features.myorders.data.remote

import android.util.Log
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.enums.UserRole
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MyOrdersRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : MyOrdersRemoteDataSource {

    private val saleOrdersRef = firestore.collection("orders")
        .document("sale")
        .collection("items")
        .orderBy("date", Query.Direction.DESCENDING)

    private val marketOrdersRef = firestore.collection("orders")
        .document("market")
        .collection("items")
        .orderBy("date", Query.Direction.DESCENDING)

    private fun currentUserId(): String = auth.currentUser?.uid ?: ""

    override suspend fun fetchCompanyOrders(): List<Order> {
        return try {
            val snapshot = saleOrdersRef.get().await()
            snapshot.documents.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null

                val scrapsList = (data["scraps"] as? List<Map<String, Any>>)?.map { scrapMap ->
                    Scrap(amount = scrapMap["amount"]?.toString() ?: "")
                } ?: emptyList()

                Order(
                    orderId = doc.id,
                    userId = data["userId"] as? String ?: "",
                    scraps = scrapsList,
                    type = (data["type"] as? String)?.let { OrderType.valueOf(it) } ?: OrderType.SALE,
                    status = (data["status"] as? String)?.let { OrderStatus.valueOf(it) } ?: OrderStatus.PENDING,
                    date = data["date"] as? Long ?: System.currentTimeMillis(),
                    userRole = (data["userRole"] as? String)?.let { UserRole.valueOf(it) } ?: UserRole.SELLER,
                    title = data["title"] as? String ?: "",
                    description = data["description"] as? String ?: "",
                    price = (data["price"] as? Long)?.toInt() ?: 0
                )
            }
        } catch (e: Exception) {
            Log.e("MyOrdersRemoteDataSource", "Error fetching company orders", e)
            emptyList()
        }
    }

    override suspend fun fetchSells(): List<Order> {
        val myUserId = currentUserId()
        if (myUserId.isEmpty()) return emptyList()

        return try {
            val snapshot = marketOrdersRef.get().await()

            snapshot.documents.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null

                if (data["userId"] != myUserId) return@mapNotNull null

                val scrapsList = (data["scraps"] as? List<Map<String, Any>>)?.map { scrapMap ->
                    Scrap(amount = scrapMap["amount"]?.toString() ?: "")
                } ?: emptyList()

                Order(
                    orderId = doc.id,
                    userId = data["userId"] as? String ?: "",
                    scraps = scrapsList,
                    type = (data["type"] as? String)?.let { OrderType.valueOf(it) } ?: OrderType.SALE,
                    status = (data["status"] as? String)?.let { OrderStatus.valueOf(it) } ?: OrderStatus.PENDING,
                    date = data["date"] as? Long ?: System.currentTimeMillis(),
                    userRole = (data["userRole"] as? String)?.let { UserRole.valueOf(it) } ?: UserRole.SELLER,
                    title = data["title"] as? String ?: "",
                    description = data["description"] as? String ?: "",
                    price = (data["price"] as? Long)?.toInt() ?: 0,
                )
            }
        } catch (e: Exception) {
            Log.e("MyOrdersRemoteDataSource", "Error fetching sells", e)
            emptyList()
        }
    }

    override suspend fun fetchOffers(): List<Order> {
        val myUserId = currentUserId()
        Log.d("MyOrdersRemoteDataSource", "fetchOffers() called, currentUserId: $myUserId")
        if (myUserId.isEmpty()) return emptyList()

        return try {
            val orderIds = fetchMyOfferOrderIds(myUserId)
            Log.d("MyOrdersRemoteDataSource", "Order IDs with my offers: $orderIds")
            if (orderIds.isEmpty()) return emptyList()

            val orders = fetchOrdersByIds(orderIds)
            Log.d("MyOrdersRemoteDataSource", "Orders fetched: ${orders.size}")
            orders
        } catch (e: Exception) {
            Log.e("MyOrdersRemoteDataSource", "Error fetching offers", e)
            emptyList()
        }
    }

    private suspend fun fetchMyOfferOrderIds(myUserId: String): List<String> {
        return try {
            val offersSnapshot = firestore.collection("Offers")
                .whereEqualTo("buyerId", myUserId)
                .get()
                .await()

            Log.d("MyOrdersRemoteDataSource", "Offers snapshot size: ${offersSnapshot.size()}")
            offersSnapshot.documents.mapNotNull { it.getString("orderId") }
        } catch (e: Exception) {
            Log.e("MyOrdersRemoteDataSource", "Error fetching offer IDs", e)
            emptyList()
        }
    }

    private suspend fun fetchOrdersByIds(orderIds: List<String>): List<Order> {
        return try {
            val ordersList = mutableListOf<Order>()
            val chunks = orderIds.chunked(10)
            for (chunk in chunks) {
                Log.d("MyOrdersRemoteDataSource", "Fetching orders for chunk: $chunk")
                val snapshot = marketOrdersRef
                    .whereIn(FieldPath.documentId(), chunk)
                    .get()
                    .await()

                snapshot.documents.mapNotNull { doc ->
                    val data = doc.data ?: return@mapNotNull null

                    val scrapsList = (data["scraps"] as? List<Map<String, Any>>)?.map { scrapMap ->
                        Scrap(amount = scrapMap["amount"]?.toString() ?: "")
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
                }.also { ordersList.addAll(it) }
            }
            ordersList
        } catch (e: Exception) {
            Log.e("MyOrdersRemoteDataSource", "Error fetching orders by IDs", e)
            emptyList()
        }
    }

}
