package com.delighted2wins.souqelkhorda.features.sell.domain.usecase

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.OrderRepository
import javax.inject.Inject

class SendOrderUseCase @Inject constructor(
    private val repo: OrderRepository
) {
    suspend operator fun invoke(order: Order) = repo.sendOrder(order)
}