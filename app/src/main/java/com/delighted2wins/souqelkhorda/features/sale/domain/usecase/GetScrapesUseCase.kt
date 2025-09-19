package com.delighted2wins.souqelkhorda.features.sale.domain.usecase

import com.delighted2wins.souqelkhorda.features.additem.domain.repo.ScrapesRepo
import javax.inject.Inject

class GetScrapesUseCase @Inject constructor(
    private val repo: ScrapesRepo
) {
    operator fun invoke() = repo.getScraps()
}