package com.delighted2wins.souqelkhorda.core.notification.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)
