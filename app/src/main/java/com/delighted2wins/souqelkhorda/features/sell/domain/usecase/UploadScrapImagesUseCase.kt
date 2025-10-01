package com.delighted2wins.souqelkhorda.features.sell.domain.usecase

import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.OrderRepository
import javax.inject.Inject

class UploadScrapImagesUseCase @Inject constructor(
    private val repo: OrderRepository
) {
    suspend operator fun invoke(scraps: List<Scrap>) = repo.uploadScrapImages(scraps)
}