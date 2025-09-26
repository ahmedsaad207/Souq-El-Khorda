package com.delighted2wins.souqelkhorda.core.notification.data

interface FcmRemoteDataSource {
    suspend fun sendNotification(request: NotificationRequestDto): ApiResponseDto<Unit>
}