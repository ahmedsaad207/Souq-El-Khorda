package com.delighted2wins.souqelkhorda.features.history.domain.usecase

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.history.domain.repository.HistoryRepository
import javax.inject.Inject

class AddOrderToHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(order : Order): Boolean {
        return repository.addOrder(order)
    }
}