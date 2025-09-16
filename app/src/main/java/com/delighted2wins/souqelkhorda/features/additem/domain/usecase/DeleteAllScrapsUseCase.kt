package com.delighted2wins.souqelkhorda.features.additem.domain.usecase

import com.delighted2wins.souqelkhorda.features.additem.domain.repo.ScrapesRepo
import javax.inject.Inject

class DeleteAllScrapsUseCase @Inject constructor(
    private val repo: ScrapesRepo
) {
    suspend operator fun invoke() = repo.deleteAllScraps()
}