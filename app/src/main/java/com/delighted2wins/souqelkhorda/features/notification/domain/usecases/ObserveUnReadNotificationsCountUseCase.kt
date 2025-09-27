package com.delighted2wins.souqelkhorda.features.notification.domain.usecases

import com.delighted2wins.souqelkhorda.features.notification.domain.repository.NotificationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUnReadNotificationsCountUseCase @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) {
    suspend operator fun invoke(): Flow<Int> = notificationsRepository.observeUnReadNotificationsCount()
}