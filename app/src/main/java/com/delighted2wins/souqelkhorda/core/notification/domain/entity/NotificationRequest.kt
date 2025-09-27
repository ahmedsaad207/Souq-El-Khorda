package com.delighted2wins.souqelkhorda.core.notification.domain.entity

data class NotificationRequest(
    val token: String? = null,
    val toUserId: String? = null,
    val title: String = "",
    val message: String,
    val imageUrl: String? = null,
    val action: String? = null,
    val extraData: Map<String, String>? = null
)
