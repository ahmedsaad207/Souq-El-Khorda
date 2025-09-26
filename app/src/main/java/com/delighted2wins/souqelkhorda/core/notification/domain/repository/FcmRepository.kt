package com.delighted2wins.souqelkhorda.core.notification.domain.repository

import com.delighted2wins.souqelkhorda.core.notification.domain.entity.NotificationRequest
import com.delighted2wins.souqelkhorda.core.notification.domain.entity.NotificationResponse

interface FcmRepository {
    suspend fun sendNotification(request: NotificationRequest): NotificationResponse<Unit>
}