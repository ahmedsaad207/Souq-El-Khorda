package com.delighted2wins.souqelkhorda.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.internet.ConnectivityObserver
import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.ObserveUserUseCase
import com.delighted2wins.souqelkhorda.features.notification.domain.usecases.ObserveUnReadNotificationsCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeUserUseCase: ObserveUserUseCase,
    private val observeUnReadNotificationsCountUseCase: ObserveUnReadNotificationsCountUseCase,
    private val connectivityObserver: ConnectivityObserver

) : ViewModel() {
    val isOnline: LiveData<Boolean> = connectivityObserver.isOnline

    private val _state = MutableStateFlow(MainActivityState())
    val state = _state.asStateFlow()

    val user: StateFlow<AuthUser?> = observeUserUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        observeNotificationsCount()
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