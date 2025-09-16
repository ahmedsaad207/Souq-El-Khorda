package com.delighted2wins.souqelkhorda.features.additem.domain.usecase

import com.delighted2wins.souqelkhorda.features.additem.domain.repo.ScrapesRepo
import com.delighted2wins.souqelkhorda.features.sale.domain.entities.Scrap
import javax.inject.Inject

class SaveScrapUseCase @Inject constructor(
    private val repo: ScrapesRepo
) {

    suspend operator fun invoke(scrap: Scrap) {
        repo.saveScrap(scrap)
    }
}