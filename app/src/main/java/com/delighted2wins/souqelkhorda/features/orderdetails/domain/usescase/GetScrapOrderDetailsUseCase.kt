package com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase

import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.repository.OrderDetailsRepository
import javax.inject.Inject

class GetScrapOrderDetailsUseCase @Inject constructor(
    private val repository: OrderDetailsRepository
) {
    suspend operator fun invoke(orderId: String): Order? {
        return repository.getScrapOrderDetails(orderId)
    }
}


