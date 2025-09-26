package com.delighted2wins.souqelkhorda.core.notification.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationRequestDto(
    val toToken: String,
    val title: String,
    val message: String,
    val imageUrl: String? = null,
    val action: String? = null,
    val extraData: Map<String, String>? = null
)
