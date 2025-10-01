package com.delighted2wins.souqelkhorda.core.notification.domain.usecases

import com.delighted2wins.souqelkhorda.core.notification.domain.entity.NotificationRequest
import com.delighted2wins.souqelkhorda.core.notification.domain.entity.NotificationResponse
import com.delighted2wins.souqelkhorda.core.notification.domain.repository.FcmRepository
import javax.inject.Inject

class SendNotificationUseCase @Inject constructor(
    private val fcmRepository: FcmRepository
) {
    suspend operator fun invoke(request: NotificationRequest): NotificationResponse<Unit> {
        return fcmRepository.sendNotification(request)
    }
}