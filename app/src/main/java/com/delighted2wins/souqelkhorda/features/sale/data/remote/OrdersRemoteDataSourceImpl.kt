package com.delighted2wins.souqelkhorda.features.sale.data.remote

import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Order
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrdersRemoteDataSourceImpl @Inject constructor(
//    private val firestore: FirebaseFirestore
) : OrdersRemoteDataSource {
    override suspend fun sendOrder(order: Order) {
//        firestore
//            .collection("orders")
//            .add(order)
//            .await()
    }
}