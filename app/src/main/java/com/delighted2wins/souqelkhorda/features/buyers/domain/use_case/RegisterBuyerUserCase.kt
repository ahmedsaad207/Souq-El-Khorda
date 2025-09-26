package com.delighted2wins.souqelkhorda.features.buyers.domain.use_case

import com.delighted2wins.souqelkhorda.features.buyers.data.model.BuyerDto
import com.delighted2wins.souqelkhorda.features.buyers.domain.repo.IBuyerRepo
import javax.inject.Inject

class RegisterBuyersCase @Inject constructor(val repo: IBuyerRepo) {
    suspend operator fun invoke(buyerDto: BuyerDto) = repo.registerBuyer(buyerDto)
}