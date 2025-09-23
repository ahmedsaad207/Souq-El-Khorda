package com.delighted2wins.souqelkhorda.features.offers.domain.usecase

import com.delighted2wins.souqelkhorda.features.offers.domain.repository.OffersRepository
import javax.inject.Inject

class DeleteOfferUseCase @Inject constructor(
    private val repository: OffersRepository
) {
    suspend operator fun invoke(offerId: String) {
        repository.deleteOffer(offerId)
    }
}