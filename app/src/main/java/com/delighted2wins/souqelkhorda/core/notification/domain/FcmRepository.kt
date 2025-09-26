package com.delighted2wins.souqelkhorda.core.notification.domain

import com.delighted2wins.souqelkhorda.core.notification.data.ApiResponseDto
import com.delighted2wins.souqelkhorda.core.notification.data.NotificationRequestDto

interface FcmRepository {
    suspend fun sendNotification(request: NotificationRequestDto): ApiResponseDto<Unit>
}