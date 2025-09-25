package com.delighted2wins.souqelkhorda.features.profile.data.repository

import com.delighted2wins.souqelkhorda.features.authentication.data.local.IAuthenticationLocalDataSource
import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser
import com.delighted2wins.souqelkhorda.features.profile.data.local.ProfileLocalDataSource
import com.delighted2wins.souqelkhorda.features.profile.data.remote.ProfileRemoteDataSource
import com.delighted2wins.souqelkhorda.features.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val authLocalDataSource: IAuthenticationLocalDataSource,
    private val profileLocalDataSource: ProfileLocalDataSource
) : ProfileRepository {

    override suspend fun updateName(name: String): Result<Unit> {
        val userId = authLocalDataSource.getCashedUser().id
        return profileRemoteDataSource.updateName(userId, name).onSuccess {
            val cachedUser = authLocalDataSource.getCashedUser()
            authLocalDataSource.cashUserData(cachedUser.copy(name = name))
        }
    }

    override suspend fun updateEmail(email: String): Result<Unit> {
        val userId = authLocalDataSource.getCashedUser().id
        return profileRemoteDataSource.updateEmail(userId, email).onSuccess {
            val cachedUser = authLocalDataSource.getCashedUser()
            authLocalDataSource.cashUserData(cachedUser.copy(email = email))
        }
    }

    override suspend fun updatePhone(phone: String): Result<Unit> {
        val userId = authLocalDataSource.getCashedUser().id
        return profileRemoteDataSource.updatePhone(userId, phone).onSuccess {
            val cachedUser = authLocalDataSource.getCashedUser()
            authLocalDataSource.cashUserData(cachedUser.copy(phone = phone))
        }
    }

    override suspend fun updateGovernorate(governorate: String): Result<Unit> {
        val userId = authLocalDataSource.getCashedUser().id
        return profileRemoteDataSource.updateGovernorate(userId, governorate).onSuccess {
            val cachedUser = authLocalDataSource.getCashedUser()
            authLocalDataSource.cashUserData(cachedUser.copy(governorate = governorate))
        }
    }

    override suspend fun updateAddress(address: String): Result<Unit> {
        val userId = authLocalDataSource.getCashedUser().id
        return profileRemoteDataSource.updateAddress(userId, address).onSuccess {
            val cachedUser = authLocalDataSource.getCashedUser()
            authLocalDataSource.cashUserData(cachedUser.copy(address = address))
        }
    }

    override suspend fun uploadAndUpdateUserImage(imageUrl: String): Result<Unit> {
        val userId = authLocalDataSource.getCashedUser().id
        return profileRemoteDataSource.uploadAndUpdateUserImage(userId, imageUrl).onSuccess {
            val cachedUser = authLocalDataSource.getCashedUser()
            authLocalDataSource.cashUserData(cachedUser.copy(imageUrl = imageUrl))
        }
    }

    override suspend fun getUserProfile(): Result<AuthUser> {
        return try {
            val cachedUser = authLocalDataSource.getCashedUser()
            if (cachedUser.id.isNotEmpty()) {
                Result.success(cachedUser)
            } else {
                Result.failure(Exception("No cached user found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun saveLanguage(code: String) {
        profileLocalDataSource.saveLanguage(code)
    }

    override fun getLanguage(): String {
        return profileLocalDataSource.getLanguage()
    }
}
