package com.delighted2wins.souqelkhorda.features.profile.data.remote

import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDb: FirebaseFirestore
): ProfileRemoteDataSource {

    private val userCollection = firebaseDb.collection("users")

    override suspend fun updateName(
        userId: String,
        name: String
    ): Result<Unit> {
        return updateUserField(userId, "name", name)
    }

    override suspend fun updateEmail(
        userId: String,
        email: String
    ): Result<Unit> {
        return try {
            firebaseAuth.currentUser?.updateEmail(email)?.await()
            updateUserField(userId, "email", email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePhone(
        userId: String,
        phone: String
    ): Result<Unit> {
        return updateUserField(userId, "phone", phone)
    }

    override suspend fun updateGovernorate(
        userId: String,
        governorate: String
    ): Result<Unit> {
        return updateUserField(userId, "governorate", governorate)
    }

    override suspend fun updateAddress(
        userId: String,
        address: String
    ): Result<Unit> {
        return updateUserField(userId, "address", address)
    }

    override suspend fun updateImageUrl(
        userId: String,
        imageUrl: String
    ): Result<Unit> {
        return updateUserField(userId, "imageUrl", imageUrl)
    }

    override suspend fun getUserProfile(userId: String): Result<AuthUser> {
        return try {
            val snapshot = userCollection.document(userId).get().await()
            val user = snapshot.toObject(AuthUser::class.java)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun updateUserField(userId: String, field: String, value: Any): Result<Unit> {
        return try {
            userCollection.document(userId).update(field, value).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}