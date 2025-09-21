package com.delighted2wins.souqelkhorda.features.profile.domain.usecase

import com.delighted2wins.souqelkhorda.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun updateName(name: String) = repository.updateName(name)
    suspend fun updatePhone(phone: String) = repository.updatePhone(phone)
    suspend fun updateGovernorate(governorate: String) = repository.updateGovernorate(governorate)
    suspend fun updateAddress(address: String) = repository.updateAddress(address)
    suspend fun updateImageUrl(imageUrl: String) = repository.updateImageUrl(imageUrl)
}