package com.delighted2wins.souqelkhorda.features.sell.domain.usecase

import com.delighted2wins.souqelkhorda.features.sell.domain.repo.ScrapRepository
import javax.inject.Inject

class DeleteAllScrapsUseCase @Inject constructor(
    private val repo: ScrapRepository
) {
    suspend operator fun invoke() = repo.deleteAllScraps()
}