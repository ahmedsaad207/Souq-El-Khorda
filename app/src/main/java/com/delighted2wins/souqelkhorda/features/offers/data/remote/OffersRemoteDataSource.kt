package com.delighted2wins.souqelkhorda.features.offers.data.remote

import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.core.model.Offer

interface OffersRemoteDataSource {
    suspend fun makeOffer(offer: Offer): String
    suspend fun updateOfferStatus(offerId: String, newStatus: OfferStatus) : Boolean
    suspend fun deleteOffer(offerId: String): Boolean
    suspend fun getOfferById(offerId: String): Offer?
    suspend fun getOffersByBuyerId(buyerId: String): List<Offer>
    suspend fun getOffersByOrderId(orderId: String): List<Offer>
    suspend fun deleteOffersByOrderId(orderId: String) : Boolean
}