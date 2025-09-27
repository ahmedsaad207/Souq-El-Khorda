package com.delighted2wins.souqelkhorda.core.notification.data.remote.service

import com.delighted2wins.souqelkhorda.core.notification.data.model.ApiResponseDto
import com.delighted2wins.souqelkhorda.core.notification.data.model.NotificationRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApiService {
    @POST("fcm/send-notification")
    suspend fun sendNotification(@Body request: NotificationRequestDto): ApiResponseDto<Unit>
}