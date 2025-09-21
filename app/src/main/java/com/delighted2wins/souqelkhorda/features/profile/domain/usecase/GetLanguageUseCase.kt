package com.delighted2wins.souqelkhorda.features.profile.domain.usecase

import com.delighted2wins.souqelkhorda.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject


class GetLanguageUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(): String = repository.getLanguage()
}