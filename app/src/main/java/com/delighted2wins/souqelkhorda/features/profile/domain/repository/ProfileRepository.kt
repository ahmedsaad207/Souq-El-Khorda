package com.delighted2wins.souqelkhorda.features.profile.domain.repository

import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser

interface ProfileRepository {
    suspend fun updateName(name: String): Result<Unit>
    suspend fun updateEmail(email: String): Result<Unit>
    suspend fun updatePhone(phone: String): Result<Unit>
    suspend fun updateGovernorate(governorate: String): Result<Unit>
    suspend fun updateAddress(address: String): Result<Unit>
    suspend fun updateImageUrl(imageUrl: String): Result<Unit>
    suspend fun getUserProfile(): Result<AuthUser>

    fun saveLanguage(code: String)
    fun getLanguage(): String
}