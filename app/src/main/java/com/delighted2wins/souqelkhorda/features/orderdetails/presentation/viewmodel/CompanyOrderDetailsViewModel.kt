package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserDataByIdUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase.GetOrderDetailsUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.CompanyOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.CompanyOrderDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyOrderDetailsViewModel @Inject constructor(
    private val getOrderDetails: GetOrderDetailsUseCase,
    private val getUserDataById: GetUserDataByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CompanyOrderDetailsState>(CompanyOrderDetailsState.Empty)
    val state: StateFlow<CompanyOrderDetailsState> = _state

    private var lastOrderId: String? = null
    private var lastOwnerId: String? = null

    fun onIntent(intent: CompanyOrderDetailsIntent) {
        when (intent) {
            is CompanyOrderDetailsIntent.LoadOrder -> {
                lastOrderId = intent.orderId
                lastOwnerId = intent.ownerId
                loadOrder(intent.orderId, intent.ownerId)
            }
            is CompanyOrderDetailsIntent.Refresh -> {
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
            _state.value = CompanyOrderDetailsState.Loading
            try {
                val order = getOrderDetails(orderId, OrderSource.COMPANY)
                val seller = getUserDataById(order?.userId ?: orderOwnerId)
                if (order != null) {
                    _state.value = CompanyOrderDetailsState.Success(order, seller)
                } else {
                    _state.value = CompanyOrderDetailsState.Empty
                }
            } catch (e: Exception) {
                _state.value = CompanyOrderDetailsState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
