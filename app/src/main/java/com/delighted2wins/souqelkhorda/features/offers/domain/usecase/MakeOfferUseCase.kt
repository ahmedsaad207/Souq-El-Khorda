package com.delighted2wins.souqelkhorda.features.offers.domain.usecase

import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.features.offers.domain.repository.OffersRepository
import javax.inject.Inject

class MakeOfferUseCase @Inject constructor(
    private val repository: OffersRepository
) {
    suspend operator fun invoke(offer: Offer): String {
        return repository.makeOffer(offer)
    }
}