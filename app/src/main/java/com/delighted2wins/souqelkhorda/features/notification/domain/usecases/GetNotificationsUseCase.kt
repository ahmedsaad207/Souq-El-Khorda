package com.delighted2wins.souqelkhorda.features.notification.domain.usecases

import com.delighted2wins.souqelkhorda.features.notification.domain.repository.NotificationsRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
   private val notificationsRepository: NotificationsRepository
) {
    suspend operator fun invoke() = notificationsRepository.getUnreadNotifications()
}