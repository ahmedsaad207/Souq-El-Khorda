package com.delighted2wins.souqelkhorda.features.sale.domain.usecase

import com.delighted2wins.souqelkhorda.features.additem.domain.repo.ScrapesRepo
import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Order
import com.delighted2wins.souqelkhorda.features.sale.domain.repo.OrdersRepository
import javax.inject.Inject

class SendOrderUseCase @Inject constructor(
    private val repo: OrdersRepository
) {
    suspend operator fun invoke(order: Order) = repo.sendOrder(order)
}