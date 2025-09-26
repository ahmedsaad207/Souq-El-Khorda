package com.delighted2wins.souqelkhorda.core.notification.data.repository

import com.delighted2wins.souqelkhorda.core.notification.data.mappers.toDomain
import com.delighted2wins.souqelkhorda.core.notification.data.mappers.toDto
import com.delighted2wins.souqelkhorda.core.notification.data.remote.datasource.FcmRemoteDataSource
import com.delighted2wins.souqelkhorda.core.notification.domain.entity.NotificationRequest
import com.delighted2wins.souqelkhorda.core.notification.domain.entity.NotificationResponse
import com.delighted2wins.souqelkhorda.core.notification.domain.repository.FcmRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FcmRepositoryImpl @Inject constructor(
    private val remoteDataSource: FcmRemoteDataSource,
    private val firebaseDb: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
) : FcmRepository {
    override suspend fun sendNotification(request: NotificationRequest): NotificationResponse<Unit> {
        val docSnapshot = firebaseDb
            .collection("users")
            .document(request.toUserId ?: firebaseAuth.currentUser!!.uid)
            .get()
            .await()

        val token = docSnapshot.getString("fcmToken")
        val name = docSnapshot.getString("name") ?: ""
        val imageUrl = docSnapshot.getString("imageUrl")

        return remoteDataSource.sendNotification(request.copy(title = name, imageUrl = imageUrl, toUserId = token).toDto()).toDomain()
    }
}