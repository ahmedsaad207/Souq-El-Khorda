package com.delighted2wins.souqelkhorda.features.notification.data.remote

import com.delighted2wins.souqelkhorda.features.notification.data.model.NotificationDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : NotificationRemoteDataSource {

    override suspend fun getNotifications(): Result<List<NotificationDto>> {
        val userId = firebaseAuth.currentUser?.uid
            ?: return Result.success(emptyList())

        return try {
            val list = firestore.collection("notifications")
                .document(userId)
                .collection("userNotifications")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
                .map { doc ->
                    doc.toObject(NotificationDto::class.java).copy(id = doc.id)
                }
            Result.success(list)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }

    override suspend fun getUnReadNotificationsCount(): Result<Int> {
        val userId = firebaseAuth.currentUser?.uid ?: return Result.success(0)
        return try {
            val count = firestore.collection("notifications")
                .document(userId)
                .collection("userNotifications")
                .whereEqualTo("read", false)
                .get()
                .await()
                .size()
            Result.success(count)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
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
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }

    override suspend fun deleteNotification(notificationId: String): Result<Unit> {
        val userId = firebaseAuth.currentUser?.uid
            ?: return Result.failure(Exception("User not logged in"))

        return try {
            firestore.collection("notifications")
                .document(userId)
                .collection("userNotifications")
                .document(notificationId)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }

    override suspend fun observeNotifications(): Flow<List<NotificationDto>> =
        firebaseAuth.authStateFlow().flatMapLatest { user ->
            if (user == null) {
                flowOf(emptyList())
            } else {
                callbackFlow {
                    val listener = firestore.collection("notifications")
                        .document(user.uid)
                        .collection("userNotifications")
                        .orderBy("createdAt", Query.Direction.DESCENDING)
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
        }


    override suspend fun observeUnReadNotificationsCount(): Flow<Int> =
        firebaseAuth.authStateFlow().flatMapLatest { user ->
            if (user == null) {
                flowOf(0)
            } else {
                callbackFlow {
                    val listener = firestore.collection("notifications")
                        .document(user.uid)
                        .collection("userNotifications")
                        .whereEqualTo("read", false)
                        .addSnapshotListener { snapshot, error ->
                            if (error != null) {
                                close(error)
                            } else {
                                trySend(snapshot?.size() ?: 0)
                            }
                        }
                    awaitClose { listener.remove() }
                }
            }
        }


    private fun FirebaseAuth.authStateFlow(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }
        addAuthStateListener(listener)
        awaitClose { removeAuthStateListener(listener) }
    }
}
