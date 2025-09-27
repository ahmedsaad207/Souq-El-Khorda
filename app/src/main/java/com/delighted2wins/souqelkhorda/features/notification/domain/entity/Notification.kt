package com.delighted2wins.souqelkhorda.features.notification.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val imageUrl: String? = null,
    val createdAt: Long = 0L,
    val read: Boolean = false,
    val type: String = ""
)
