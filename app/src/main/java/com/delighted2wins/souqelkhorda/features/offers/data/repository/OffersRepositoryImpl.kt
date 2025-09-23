package com.delighted2wins.souqelkhorda.features.offers.data.repository

import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.features.offers.data.remote.OffersRemoteDataSource
import com.delighted2wins.souqelkhorda.features.offers.domain.repository.OffersRepository
import javax.inject.Inject

class OffersRepositoryImpl @Inject constructor(
    private val remoteDataSource: OffersRemoteDataSource
) : OffersRepository {

    override suspend fun makeOffer(offer: Offer): String {
        return remoteDataSource.makeOffer(offer)
    }

    override suspend fun updateOfferStatus(offerId: String, newStatus: OfferStatus) {
        remoteDataSource.updateOfferStatus(offerId, newStatus)
    }

    override suspend fun deleteOffer(offerId: String) {
        remoteDataSource.deleteOffer(offerId)
    }

    override suspend fun getOfferById(offerId: String): Offer? {
        return remoteDataSource.getOfferById(offerId)
    }

    override suspend fun getOffersByBuyerId(buyerId: String): List<Offer> {
        return remoteDataSource.getOffersByBuyerId(buyerId)
    }

    override suspend fun getOffersByOrderId(orderId: String): List<Offer> {
        return remoteDataSource.getOffersByOrderId(orderId)
    }
}
