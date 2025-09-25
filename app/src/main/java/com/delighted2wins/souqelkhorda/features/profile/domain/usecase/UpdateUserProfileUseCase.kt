package com.delighted2wins.souqelkhorda.features.profile.domain.usecase

import com.delighted2wins.souqelkhorda.features.profile.domain.entity.ProfileMessagesEnum
import com.delighted2wins.souqelkhorda.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject


class UpdateUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun updateName(name: String): Result<Unit> {
        if (name.isBlank()) {
            return Result.failure(IllegalArgumentException(ProfileMessagesEnum.NAME_INVALID.getMsg()))
        }
        return repository.updateName(name)
    }

    suspend fun updatePhone(phone: String): Result<Unit> {
        val egyptPhoneRegex = Regex("^01[0-25][0-9]{8}$")

        if (!egyptPhoneRegex.matches(phone)) {
            return Result.failure(
                IllegalArgumentException(ProfileMessagesEnum.PHONE_INVALID.getMsg())
            )
        }

        return repository.updatePhone(phone)
    }

    suspend fun updateGovernorate(governorate: String): Result<Unit> {
        if (governorate.isBlank()) {
            return Result.failure(IllegalArgumentException(ProfileMessagesEnum.GOVERNORATE_INVALID.getMsg()))
        }
        return repository.updateGovernorate(governorate)
    }

    suspend fun updateAddress(address: String): Result<Unit> {
        if (address.isBlank() || address.length < 15) {
            return Result.failure(IllegalArgumentException(ProfileMessagesEnum.ADDRESS_INVALID.getMsg()))
        }
        return repository.updateAddress(address)
    }

    suspend fun uploadAndUpdateUserImage(imageUrl: String): Result<Unit> {
        if (imageUrl.isBlank()) {
            return Result.failure(IllegalArgumentException(ProfileMessagesEnum.IMAGE_INVALID.getMsg()))
        }
        return repository.uploadAndUpdateUserImage(imageUrl)
    }
}
