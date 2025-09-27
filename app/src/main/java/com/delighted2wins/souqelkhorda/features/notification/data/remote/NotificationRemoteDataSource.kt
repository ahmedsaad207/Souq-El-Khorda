package com.delighted2wins.souqelkhorda.features.notification.data.remote

import com.delighted2wins.souqelkhorda.features.notification.data.model.NotificationDto
import kotlinx.coroutines.flow.Flow

interface NotificationRemoteDataSource {
    suspend fun getUnreadNotifications(): Result<List<NotificationDto>>
    suspend fun markAsRead(notificationId: String): Result<Unit>
    suspend fun deleteNotification(notificationId: String): Result<Unit>
    suspend fun observeNotifications(): Flow<List<NotificationDto>>
}