package com.delighted2wins.souqelkhorda.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.internet.ConnectivityObserver
import com.delighted2wins.souqelkhorda.features.notification.domain.usecases.GetUnReadNotificationsCountUseCase
import com.delighted2wins.souqelkhorda.features.notification.domain.usecases.ObserveUnReadNotificationsCountUseCase
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getUnReadNotificationsCountUseCase: GetUnReadNotificationsCountUseCase,
    private val observeUnReadNotificationsCountUseCase: ObserveUnReadNotificationsCountUseCase,
    private val connectivityObserver: ConnectivityObserver

): ViewModel() {
    val isOnline: LiveData<Boolean> = connectivityObserver.isOnline

    private val _state = MutableStateFlow(MainActivityState())
    val state = _state.asStateFlow()

    init {
        loadUser()
        observeNotificationsCount()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val result = getUserProfileUseCase()
            result.onSuccess { user ->
                _state.value = _state.value.copy(
                    userName = user.name,
                    userImageUrl = user.imageUrl
                )
            }.onFailure {

            }
        }
    }

    private fun loadNotificationsCount() {
        viewModelScope.launch {
            val result = getUnReadNotificationsCountUseCase()
            result.onSuccess { count ->
                _state.value = _state.value.copy(
                    notificationCount = count
                )
            }.onFailure {

            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        connectivityObserver.unregister()
    }
    private fun observeNotificationsCount() {
        viewModelScope.launch {
            observeUnReadNotificationsCountUseCase()
                .collect { count ->
                    _state.value = _state.value.copy(notificationCount = count)
                }
        }
    }
}