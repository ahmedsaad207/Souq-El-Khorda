package com.delighted2wins.souqelkhorda.core.notification.domain

import com.delighted2wins.souqelkhorda.core.notification.data.ApiResponseDto
import com.delighted2wins.souqelkhorda.core.notification.data.NotificationRequestDto
import javax.inject.Inject

class SendNotificationUseCase @Inject constructor(
    private val fcmRepository: FcmRepository
) {
    suspend operator fun invoke(request: NotificationRequestDto): ApiResponseDto<Unit> {
        return fcmRepository.sendNotification(request)
    }
}