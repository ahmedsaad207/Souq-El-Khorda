package com.delighted2wins.souqelkhorda.features.profile.domain.usecase

import com.delighted2wins.souqelkhorda.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateUserEmailUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(email: String) = repository.updateEmail(email)
}