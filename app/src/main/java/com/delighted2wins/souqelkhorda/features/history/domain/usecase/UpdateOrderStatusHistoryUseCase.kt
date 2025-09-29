package com.delighted2wins.souqelkhorda.features.history.domain.usecase

import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.features.history.domain.repository.HistoryRepository
import javax.inject.Inject

class UpdateOrderStatusHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(orderId: String, userId: String,orderType: String, status: OrderStatus): Boolean {
        return repository.updateOrderStatus(orderId, userId,orderType, status)
    }
}
