package com.delighted2wins.souqelkhorda.core.notification.data

import com.delighted2wins.souqelkhorda.core.notification.domain.FcmRepository
import com.delighted2wins.souqelkhorda.features.authentication.data.local.IAuthenticationLocalDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FcmRepositoryImpl @Inject constructor(
    private val remoteDataSource: FcmRemoteDataSource,
    private val authLocalDataSource: IAuthenticationLocalDataSource,
    private val firebaseDb: FirebaseFirestore
) : FcmRepository {
    override suspend fun sendNotification(request: NotificationRequestDto): ApiResponseDto<Unit> {
        val userId = authLocalDataSource.getCashedUser().id
        val token = firebaseDb.collection("users").document(userId).get().await()
            .getString("fcmToken")

        return remoteDataSource.sendNotification(request.copy(toToken = token ?: ""))
    }
}