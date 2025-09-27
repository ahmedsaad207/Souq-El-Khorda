package com.delighted2wins.souqelkhorda.features.history.domain.usecase

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.history.domain.repository.HistoryRepository
import javax.inject.Inject

class AddOrderOfferToHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(order: Order, buyerId: String): Boolean {
        return historyRepository.addOrderOffer(order, buyerId)
    }
}