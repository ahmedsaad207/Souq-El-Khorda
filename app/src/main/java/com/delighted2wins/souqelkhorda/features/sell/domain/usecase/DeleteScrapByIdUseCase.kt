package com.delighted2wins.souqelkhorda.features.sell.domain.usecase

import com.delighted2wins.souqelkhorda.features.sell.domain.repo.ScrapRepository
import javax.inject.Inject

class DeleteScrapByIdUseCase @Inject constructor(
    private val repo: ScrapRepository
) {
    operator fun invoke(id: Int) = repo.deleteScrapById(id)
}