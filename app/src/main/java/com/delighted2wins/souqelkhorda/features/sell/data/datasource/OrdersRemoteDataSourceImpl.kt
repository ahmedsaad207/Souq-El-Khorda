package com.delighted2wins.souqelkhorda.features.sell.data.datasource

import androidx.core.net.toUri
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.data.remote.cloudinary.CloudinaryService
import com.delighted2wins.souqelkhorda.features.sell.data.remote.firestore.FirestoreOrderService
import javax.inject.Inject

class OrdersRemoteDataSourceImpl @Inject constructor(
    private val firestoreService: FirestoreOrderService,
    private val cloudinaryService: CloudinaryService
) : OrdersRemoteDataSource {
    override suspend fun sendOrder(order: Order) {
        firestoreService.sendOrder(order)
    }

    override suspend fun deleteOrder(orderId: String): Boolean {
        return firestoreService.deleteCompanyOrder(orderId)
    }

    override suspend fun uploadScrapImages(scraps: List<Scrap>): List<Scrap> {
        return scraps.map { scrap ->
            val uploadedUrls = scrap.images.map { localUri ->
                cloudinaryService.uploadImage(localUri.toUri())
            }
            scrap.copy(images = uploadedUrls)
        }
    }
}