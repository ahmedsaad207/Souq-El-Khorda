package com.delighted2wins.souqelkhorda.features.notification.domain.repository

import com.delighted2wins.souqelkhorda.features.notification.domain.entity.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationsRepository {
    suspend fun getNotifications(): Result<List<Notification>>
    suspend fun getUnReadNotificationsCount(): Result<Int>
    suspend fun markAsRead(notificationId: String): Result<Unit>
    suspend fun deleteNotification(notificationId: String): Result<Unit>

    suspend fun observeNotifications(): Flow<List<Notification>>
    suspend fun observeUnReadNotificationsCount(): Flow<Int>
}