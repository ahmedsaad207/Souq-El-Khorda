package com.delighted2wins.souqelkhorda.features.notification.domain.usecases

import com.delighted2wins.souqelkhorda.features.notification.domain.repository.NotificationsRepository
import javax.inject.Inject

class GetUnReadNotificationsCountUseCase @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) {
    suspend operator fun invoke(): Result<Int> = notificationsRepository.getUnReadNotificationsCount()
}