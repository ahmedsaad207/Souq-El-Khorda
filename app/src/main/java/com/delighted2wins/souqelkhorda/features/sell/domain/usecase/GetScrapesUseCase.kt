package com.delighted2wins.souqelkhorda.features.sell.domain.usecase

import com.delighted2wins.souqelkhorda.features.sell.domain.repo.ScrapRepository
import javax.inject.Inject

class GetScrapesUseCase @Inject constructor(
    private val repo: ScrapRepository
) {
    operator fun invoke() = repo.getScraps()
}