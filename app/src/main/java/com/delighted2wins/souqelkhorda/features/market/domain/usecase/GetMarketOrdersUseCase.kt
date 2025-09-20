package com.delighted2wins.souqelkhorda.features.market.domain.usecase

import com.delighted2wins.souqelkhorda.features.market.domain.repository.MarketRepository
import javax.inject.Inject

class GetMarketOrdersUseCase @Inject constructor(
    private val repository: MarketRepository
) {
    suspend operator fun invoke() = repository.getScrapOrders()
}
