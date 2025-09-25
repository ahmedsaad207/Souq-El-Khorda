package com.delighted2wins.souqelkhorda.features.profile.data.remote

import androidx.core.net.toUri
import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser
import com.delighted2wins.souqelkhorda.features.sell.data.remote.cloudinary.CloudinaryService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    firebaseDb: FirebaseFirestore,
    private val cloudinaryService: CloudinaryService,
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

    override suspend fun uploadAndUpdateUserImage(
        userId: String,
        imageUri: String
    ): Result<Unit> {
        val imageUrl = cloudinaryService.uploadImage(imageUri.toUri())
        return updateUserField(userId, "imageUrl", imageUrl)
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