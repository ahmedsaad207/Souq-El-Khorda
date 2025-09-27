package com.delighted2wins.souqelkhorda.features.buyers.domain.use_case

import com.delighted2wins.souqelkhorda.features.buyers.domain.repo.IBuyerRepo
import javax.inject.Inject

class GetBuyersCase @Inject constructor(val repo: IBuyerRepo) {
    suspend operator fun invoke() = repo.getNearstBuyers()
}