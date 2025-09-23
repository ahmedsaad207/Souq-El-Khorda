package com.delighted2wins.souqelkhorda.features.profile.domain.usecase

import com.delighted2wins.souqelkhorda.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke() = repository.getUserProfile()
}