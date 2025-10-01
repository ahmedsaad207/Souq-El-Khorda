package com.delighted2wins.souqelkhorda.features.buyers.domain.use_case

import com.delighted2wins.souqelkhorda.features.buyers.domain.repo.IBuyerRepo
import javax.inject.Inject

class IsBuyersCase @Inject constructor(val repo: IBuyerRepo) {
    suspend operator fun invoke(userId: String) = repo.isBuyer(userId)
}