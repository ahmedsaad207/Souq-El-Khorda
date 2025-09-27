package com.delighted2wins.souqelkhorda.core.notification.data.remote.datasource

import com.delighted2wins.souqelkhorda.core.notification.data.model.ApiResponseDto
import com.delighted2wins.souqelkhorda.core.notification.data.model.NotificationRequestDto

interface FcmRemoteDataSource {
    suspend fun sendNotification(request: NotificationRequestDto): ApiResponseDto<Unit>
}