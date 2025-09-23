package com.delighted2wins.souqelkhorda.features.offers.domain.usecase

import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.features.offers.domain.repository.OffersRepository
import javax.inject.Inject

class UpdateOfferStatusUseCase @Inject constructor(
    private val repository: OffersRepository
) {
    suspend operator fun invoke(offerId: String, newStatus: OfferStatus) {
        repository.updateOfferStatus(offerId, newStatus)
    }
}