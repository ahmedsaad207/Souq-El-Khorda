package com.delighted2wins.souqelkhorda.features.profile.domain.usecase

import com.delighted2wins.souqelkhorda.features.profile.domain.entity.ProfileMessagesEnum
import com.delighted2wins.souqelkhorda.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateUserEmailUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException(ProfileMessagesEnum.EMAIL_INVALID.getMsg()))
        }
        return repository.updateEmail(email)
    }
}