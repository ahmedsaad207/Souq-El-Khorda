package com.delighted2wins.souqelkhorda.features.notification.data.mapper

import com.delighted2wins.souqelkhorda.features.notification.data.model.NotificationDto
import com.delighted2wins.souqelkhorda.features.notification.domain.entity.Notification

fun NotificationDto.toDomain(): Notification {
    return Notification(
        id = id,
        title = title,
        message = message,
        imageUrl = imageUrl,
        createdAt = createdAt,
        read = read,
    )
}

fun Notification.toDto(): NotificationDto {
    return NotificationDto(
        id = id,
        title = title,
        message = message,
        imageUrl = imageUrl,
        createdAt = createdAt,
        read = read,
    )
}