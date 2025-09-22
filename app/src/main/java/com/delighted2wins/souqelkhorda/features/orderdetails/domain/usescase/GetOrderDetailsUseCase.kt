package com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase

import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.repository.OrderDetailsRepository
import javax.inject.Inject

class GetOrderDetailsUseCase @Inject constructor(
    private val repository: OrderDetailsRepository
) {
    suspend operator fun invoke(orderId: String, ownerId: String, buyerId: String?, source: OrderSource): Order? {
        return repository.getOrderDetails(orderId, ownerId, buyerId, source)
    }
}


