package com.delighted2wins.souqelkhorda.core.notification.data

import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApiService {
    @POST("fcm/send-notification")
    suspend fun sendNotification(@Body request: NotificationRequestDto): ApiResponseDto<Unit>
}

data class NotificationRequestDto(
    val toToken: String,
    val title: String,
    val message: String,
    val imageUrl: String? = null,
    val action: String? = null,
    val extraData: Map<String, String>? = null
)

data class ApiResponseDto<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)