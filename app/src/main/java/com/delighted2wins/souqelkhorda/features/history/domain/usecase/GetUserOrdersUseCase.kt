package com.delighted2wins.souqelkhorda.features.history.domain.usecase

import com.delighted2wins.souqelkhorda.features.history.domain.entity.History
import com.delighted2wins.souqelkhorda.features.history.domain.repository.HistoryRepository
import javax.inject.Inject

class GetUserOrdersUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(): Result<History>  = historyRepository.getUserOrders()
}