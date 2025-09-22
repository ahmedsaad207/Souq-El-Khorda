package com.delighted2wins.souqelkhorda.features.offers.data.remote

import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OffersRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : OffersRemoteDataSource {

    private val offersCollection = firestore.collection("Offers")

    override suspend fun makeOffer(offer: Offer): String {
        val docRef = offersCollection.document()
        val offerWithId = offer.copy(offerId = docRef.id)
        docRef.set(offerWithId).await()
        return docRef.id
    }

    override suspend fun updateOfferStatus(offerId: String, newStatus: OfferStatus) {
        offersCollection.document(offerId)
            .update("status", newStatus.name)
            .await()
    }

    override suspend fun deleteOffer(offerId: String) {
        offersCollection.document(offerId).delete().await()
    }

    override suspend fun getOfferById(offerId: String): Offer? {
        val snapshot = offersCollection.document(offerId).get().await()
        return if (snapshot.exists()) snapshot.toObject(Offer::class.java) else null
    }

    override suspend fun getOffersByBuyerId(buyerId: String): List<Offer> {
        val snapshot = offersCollection
            .whereEqualTo("buyerId", buyerId)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(Offer::class.java) }
    }

    override suspend fun getOffersByOrderId(orderId: String): List<Offer> {
        val snapshot = offersCollection
            .whereEqualTo("orderId", orderId)
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(Offer::class.java) }
    }
}
