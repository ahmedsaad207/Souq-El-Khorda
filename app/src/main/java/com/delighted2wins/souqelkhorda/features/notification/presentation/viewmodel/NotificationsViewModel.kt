package com.delighted2wins.souqelkhorda.features.notification.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.features.notification.domain.usecases.DismissNotificationUseCase
import com.delighted2wins.souqelkhorda.features.notification.domain.usecases.GetNotificationsUseCase
import com.delighted2wins.souqelkhorda.features.notification.domain.usecases.MarkNotificationAsReadUseCase
import com.delighted2wins.souqelkhorda.features.notification.domain.usecases.ObserveNotificationsUseCase
import com.delighted2wins.souqelkhorda.features.notification.presentation.contract.NotificationsContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    private val dismissNotificationUseCase: DismissNotificationUseCase,
    private val observeNotificationsUseCase: ObserveNotificationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationsContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<NotificationsContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        loadNotifications()
        observerNotifications()
    }

    fun handleIntent(intent: NotificationsContract.Intent) {
        when (intent) {
            is NotificationsContract.Intent.Load -> loadNotifications()
            is NotificationsContract.Intent.MarkAsRead -> markNotificationAsRead(intent.notificationId)
            is NotificationsContract.Intent.Dismiss -> dismissNotification(intent.notificationId)
            is NotificationsContract.Intent.Refresh -> refresh()
        }
    }

    private fun loadNotifications() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val result = getNotificationsUseCase()
            result.onSuccess { notifications ->
                _state.update { it.copy(isLoading = false, notifications = notifications) }
            }.onFailure { error ->
                _state.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }

    private fun markNotificationAsRead(notificationId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = markNotificationAsReadUseCase(notificationId)
            result.onSuccess {
                _state.update { state ->
                    val updatedNotifications = state.notifications.map { notification ->
                        if (notification.id == notificationId) {
                            notification.copy(read = true)
                        } else {
                            notification
                        }
                    }
                    state.copy(notifications = updatedNotifications)
                }
            }.onFailure {
                _effect.send(NotificationsContract.Effect.ShowError(it.message ?: "Unknown error"))
            }
        }
    }

    private fun dismissNotification(notificationId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dismissNotificationUseCase(notificationId)
            result.onSuccess {
                _state.update { state ->
                    val updatedNotifications = state.notifications.filterNot { it.id == notificationId }
                    state.copy(notifications = updatedNotifications)
                }
            }.onFailure {
                _effect.send(NotificationsContract.Effect.ShowError(it.message ?: "Delete failed"))
            }
        }
    }

    private fun refresh() {
        loadNotifications()
    }

    private fun observerNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            observeNotificationsUseCase().collect { notifications ->
                _state.update { it.copy(notifications = notifications) }
            }
        }
    }
}
