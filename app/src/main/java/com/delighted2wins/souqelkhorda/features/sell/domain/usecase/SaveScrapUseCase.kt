package com.delighted2wins.souqelkhorda.features.sell.domain.usecase

import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.ScrapRepository
import javax.inject.Inject

class SaveScrapUseCase @Inject constructor(
    private val repo: ScrapRepository
) {

    suspend operator fun invoke(scrap: Scrap) {
        repo.saveScrap(scrap)
    }
}