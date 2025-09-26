package com.delighted2wins.souqelkhorda.core.notification.data

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FcmRemoteDataSourceImpl @Inject constructor(
    private val fcmApiService: FcmApiService
) : FcmRemoteDataSource {
    override suspend fun sendNotification(request: NotificationRequestDto): ApiResponseDto<Unit> {
        return fcmApiService.sendNotification(request)
    }
}