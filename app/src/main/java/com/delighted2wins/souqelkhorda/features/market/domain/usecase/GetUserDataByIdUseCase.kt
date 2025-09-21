package com.delighted2wins.souqelkhorda.features.market.domain.usecase

import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.domain.repository.MarketRepository
import javax.inject.Inject

class GetUserDataByIdUseCase @Inject constructor(
    private val repository: MarketRepository
) {
    suspend operator fun invoke(userId: String) : MarketUser = repository.fetchUserForMarket(userId)

}
