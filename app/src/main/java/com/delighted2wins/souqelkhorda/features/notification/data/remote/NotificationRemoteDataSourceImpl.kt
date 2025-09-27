package com.delighted2wins.souqelkhorda.features.notification.data.remote

import com.delighted2wins.souqelkhorda.features.notification.data.model.NotificationDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : NotificationRemoteDataSource {

    override suspend fun getUnreadNotifications(): Result<List<NotificationDto>> {
        val userId = firebaseAuth.currentUser?.uid
            ?: return Result.success(emptyList())

        return try {
            val list = firestore.collection("notifications")
                .document(userId)
                .collection("userNotifications")
                .whereEqualTo("read", false)
                .get()
                .await()
                .map { doc ->
                    doc.toObject(NotificationDto::class.java).copy(id = doc.id)
                }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun markAsRead(notificationId: String): Result<Unit> {
        val userId = firebaseAuth.currentUser?.uid
            ?: return Result.failure(Exception("User not logged in"))

        return try {
            firestore.collection("notifications")
                .document(userId)
                .collection("userNotifications")
                .document(notificationId)
                .update("read", true)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun observeNotifications(): Flow<List<NotificationDto>> = callbackFlow {
        val userId = firebaseAuth.currentUser?.uid ?: run {
            close(IllegalStateException("User not logged in"))
            return@callbackFlow
        }

        val listener = firestore.collection("notifications")
            .document(userId)
            .collection("userNotifications")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                } else {
                    val notifications = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(NotificationDto::class.java)?.copy(id = doc.id)
                    }.orEmpty()
                    trySend(notifications)
                }
            }

        awaitClose { listener.remove() }
    }
}
