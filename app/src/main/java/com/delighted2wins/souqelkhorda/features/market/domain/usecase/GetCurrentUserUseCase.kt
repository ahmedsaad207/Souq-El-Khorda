package com.delighted2wins.souqelkhorda.features.market.domain.usecase

import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.domain.repository.MarketRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: MarketRepository
) {
    suspend operator fun invoke(): MarketUser {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        return repository.fetchUserForMarket(userId)
    }
}