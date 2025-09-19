package com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase

import com.delighted2wins.souqelkhorda.features.orderdetails.domain.repository.OrderDetailsRepository
import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapOrder
import javax.inject.Inject

class GetScrapOrderDetailsUseCase @Inject constructor(
    private val repository: OrderDetailsRepository
) {
    suspend operator fun invoke(orderId: String): ScrapOrder? {
        return repository.getScrapOrderDetails(orderId)
    }
}


