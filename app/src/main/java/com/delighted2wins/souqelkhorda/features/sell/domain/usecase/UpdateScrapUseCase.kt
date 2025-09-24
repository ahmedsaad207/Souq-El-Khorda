package com.delighted2wins.souqelkhorda.features.sell.domain.usecase

import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.ScrapRepository
import javax.inject.Inject

class UpdateScrapUseCase @Inject constructor(
    private val repo: ScrapRepository
) {
    operator fun invoke(scrap: Scrap) = repo.updateScrap(scrap)
}