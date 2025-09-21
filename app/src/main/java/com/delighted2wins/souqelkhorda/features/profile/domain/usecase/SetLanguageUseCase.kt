package com.delighted2wins.souqelkhorda.features.profile.domain.usecase

import com.delighted2wins.souqelkhorda.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class SetLanguageUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(code: String) = repository.saveLanguage(code)
}