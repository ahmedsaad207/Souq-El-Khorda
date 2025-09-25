package com.delighted2wins.souqelkhorda.features.profile.data.remote

import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser

interface ProfileRemoteDataSource {
    suspend fun updateName(userId: String, name: String): Result<Unit>
    suspend fun updateEmail(userId: String, email: String): Result<Unit>
    suspend fun updatePhone(userId: String, phone: String): Result<Unit>
    suspend fun updateGovernorate(userId: String, governorate: String): Result<Unit>
    suspend fun updateAddress(userId: String, address: String): Result<Unit>
    suspend fun getUserProfile(userId: String): Result<AuthUser>
    suspend fun uploadAndUpdateUserImage(userId: String, imageUri: String): Result<Unit>
}