package com.delighted2wins.souqelkhorda.features.notification.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val imageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val read: Boolean = false,
)
