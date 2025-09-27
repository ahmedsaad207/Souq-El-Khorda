package com.delighted2wins.souqelkhorda.features.notification.data.repository

import com.delighted2wins.souqelkhorda.features.notification.data.mapper.toDomain
import com.delighted2wins.souqelkhorda.features.notification.data.model.NotificationDto
import com.delighted2wins.souqelkhorda.features.notification.data.remote.NotificationRemoteDataSource
import com.delighted2wins.souqelkhorda.features.notification.domain.entity.Notification
import com.delighted2wins.souqelkhorda.features.notification.domain.repository.NotificationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor(
    private val remoteDataSource: NotificationRemoteDataSource
): NotificationsRepository {
    override suspend fun getUnreadNotifications(): Result<List<Notification>> {
        return remoteDataSource.getUnreadNotifications().map { notifications ->
            notifications.map { it.toDomain() }
        }
    }

    override suspend fun markAsRead(notificationId: String): Result<Unit> {
        return remoteDataSource.markAsRead(notificationId)
    }

    override suspend fun observeNotifications(): Flow<List<Notification>> {
        return remoteDataSource.observeNotifications().map { notifications ->
            notifications.map { it.toDomain() }
        }
    }
}