package com.delighted2wins.souqelkhorda.features.history.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.features.history.domain.usecase.GetUserOrdersUseCase
import com.delighted2wins.souqelkhorda.features.history.presentation.contract.HistoryContract
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getUserOrdersUseCase: GetUserOrdersUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
): ViewModel() {
    private val _state = MutableStateFlow(HistoryContract.State())
    val state = _state.asStateFlow()

    init {
        loadOrders()
        loadUserId()
    }

    fun handleIntent(intent: HistoryContract.Intent) {
        when (intent) {
            is HistoryContract.Intent.LoadOrders -> loadOrders()
            is HistoryContract.Intent.FilterOrders -> filterOrders(intent.tab)
        }
    }

    private fun loadOrders() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUserOrdersUseCase()
            result.onSuccess { history ->
                val orders = history.orders

                _state.update {
                    it.copy(
                        isLoading = false,
                        orders = orders,
                        completedCount = orders.count { order -> order.status == OrderStatus.COMPLETED },
                        pendingCount = orders.count { order -> order.status == OrderStatus.PENDING },
                        cancelledCount = orders.count { order -> order.status == OrderStatus.CANCELLED },
                        error = null
                    )
                }

            }.onFailure {
                _state.update { it.copy(isLoading = false,error = it.error) }
            }

        }
    }

    private fun loadUserId() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUserProfileUseCase()
            result.onSuccess { user ->
                _state.update { it.copy(userId = user.id) }
            }.onFailure {

            }
        }
    }

    private fun filterOrders(tab: Int) {
        _state.update { it.copy(selectedTabIndex = tab) }
    }
}