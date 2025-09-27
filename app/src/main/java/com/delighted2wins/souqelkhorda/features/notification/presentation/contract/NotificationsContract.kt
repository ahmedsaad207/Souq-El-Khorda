package com.delighted2wins.souqelkhorda.features.notification.presentation.contract

import com.delighted2wins.souqelkhorda.features.notification.domain.entity.Notification

interface NotificationsContract {
    data class State(
        val notifications: List<Notification> = emptyList(),
        val unreadCount: Int = 0,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed class Intent {
        data object Load : Intent()
        data class MarkAsRead(val notificationId: String) : Intent()
        data class Dismiss(val notificationId: String) : Intent()
        object Refresh : Intent()
    }

    sealed class Effect {
        data class ShowError(val message: String) : Effect()
        object NavigateBack : Effect()
    }
}