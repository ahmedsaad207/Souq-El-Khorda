package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserDataByIdUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase.GetOrderDetailsUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.MarketOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.MarketOrderDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketOrderDetailsViewModel @Inject constructor(
    private val getOrderDetails: GetOrderDetailsUseCase,
    private val getUserDataById: GetUserDataByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MarketOrderDetailsState>(MarketOrderDetailsState.Empty)
    val state: StateFlow<MarketOrderDetailsState> = _state

    private var lastOrderId: String? = null
    private var lastOwnerId: String? = null

    fun onIntent(intent: MarketOrderDetailsIntent) {
        when (intent) {
            is MarketOrderDetailsIntent.LoadOrder -> {
                lastOrderId = intent.orderId
                lastOwnerId = intent.ownerId
                loadOrder(intent.orderId, intent.ownerId)
            }
            is MarketOrderDetailsIntent.Refresh -> {
                val orderId = lastOrderId
                val ownerId = lastOwnerId
                if (orderId != null && ownerId != null) {
                    loadOrder(orderId, ownerId)
                }
            }
        }
    }

    private fun loadOrder(orderId: String, orderOwnerId: String) {
        viewModelScope.launch {
            _state.value = MarketOrderDetailsState.Loading
            try {
                val order = getOrderDetails(orderId, OrderSource.MARKET)
                val owner = getUserDataById(order?.userId ?: orderOwnerId)
                if (order != null) {
                    _state.value = MarketOrderDetailsState.Success(order, owner)
                } else {
                    _state.value = MarketOrderDetailsState.Empty
                }
            } catch (e: Exception) {
                _state.value = MarketOrderDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
