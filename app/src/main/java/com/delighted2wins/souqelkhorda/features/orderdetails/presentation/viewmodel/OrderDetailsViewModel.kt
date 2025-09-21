package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase.GetScrapOrderDetailsUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OrderDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
    private val getScrapOrderDetails: GetScrapOrderDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OrderDetailsState())
    val state: StateFlow<OrderDetailsState> = _state

    fun onIntent(intent: OrderDetailsIntent) {
        when (intent) {
            is OrderDetailsIntent.LoadOrderDetails -> loadOrder(intent.order.orderId)
            OrderDetailsIntent.BackClicked -> TODO()
            OrderDetailsIntent.Retry -> TODO()
        }
    }

    private fun loadOrder(orderId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val result = getScrapOrderDetails(orderId)
                _state.value = _state.value.copy(
                    isLoading = false,
                    order = result,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}