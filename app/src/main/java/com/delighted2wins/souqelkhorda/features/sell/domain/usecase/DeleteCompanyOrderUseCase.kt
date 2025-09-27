package com.delighted2wins.souqelkhorda.features.sell.domain.usecase

import com.delighted2wins.souqelkhorda.features.sell.domain.repo.OrderRepository
import javax.inject.Inject

class DeleteCompanyOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(orderId: String): Boolean {
        return orderRepository.deleteCompanyOrder(orderId)
    }
}