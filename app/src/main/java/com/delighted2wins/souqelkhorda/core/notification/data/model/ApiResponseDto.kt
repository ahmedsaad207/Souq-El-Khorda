package com.delighted2wins.souqelkhorda.core.notification.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseDto<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)
