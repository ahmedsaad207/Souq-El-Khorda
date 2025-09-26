package com.delighted2wins.souqelkhorda.features.myorders.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetCurrentUserUseCase
import com.delighted2wins.souqelkhorda.features.myorders.domain.usecase.LoadCompanyOrdersUseCase
import com.delighted2wins.souqelkhorda.features.myorders.domain.usecase.LoadOffersUseCase
import com.delighted2wins.souqelkhorda.features.myorders.domain.usecase.LoadSellsUseCase
import com.delighted2wins.souqelkhorda.features.myorders.presentation.contract.MyOrdersEffect
import com.delighted2wins.souqelkhorda.features.myorders.presentation.contract.MyOrdersIntents
import com.delighted2wins.souqelkhorda.features.myorders.presentation.contract.MyOrdersState
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.DeleteCompanyOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    private val loadCompanyOrdersUseCase: LoadCompanyOrdersUseCase,
    private val loadSellsUseCase: LoadSellsUseCase,
    private val loadOffersUseCase: LoadOffersUseCase,
    private val deleteCompanyOrderUseCase: DeleteCompanyOrderUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MyOrdersState())
    val state: StateFlow<MyOrdersState> = _state

    private val _effect = MutableSharedFlow<MyOrdersEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadCurrentUser()
    }

    fun onIntent(intent: MyOrdersIntents) {
        when (intent) {
            is MyOrdersIntents.LoadSaleOrders -> loadCompanyOrders()
            is MyOrdersIntents.LoadSells -> loadSells()
            is MyOrdersIntents.LoadOffers -> loadOffers()
            is MyOrdersIntents.DeleteCompanyOrder -> deleteCompanyOrder(intent.orderId)
        }
    }

    private fun loadCompanyOrders() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val result = loadCompanyOrdersUseCase()
                _state.value = _state.value.copy(
                    isLoading = false,
                    saleOrders = result,
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

    private fun loadSells() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val result = loadSellsUseCase()
                _state.value = _state.value.copy(
                    isLoading = false,
                    sells = result,
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

    private fun loadOffers() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val result = loadOffersUseCase()
                Log.d("MyOrdersViewModel", "loadOffers: $result")
                _state.value = _state.value.copy(
                    isLoading = false,
                    offers = result,
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

    private fun deleteCompanyOrder(orderId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null, isSubmitting = true)
            try {
                val isOrderDeclined = deleteCompanyOrderUseCase(orderId)
                _state.value = _state.value.copy(
                    isLoading = false,
                    isSubmitting = false,
                    error = null
                )
                loadCompanyOrders()
                if (isOrderDeclined) {
                    emitEffect(MyOrdersEffect.ShowSuccess("Order declined successfully"))
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    isSubmitting = false,
                    error = e.message ?: "Unknown error"
                )
                emitEffect(MyOrdersEffect.ShowError(e.message ?: "Unknown error"))
            }finally {
                _state.value = _state.value.copy(isSubmitting = false)
            }
        }
    }

    private fun emitEffect(effect: MyOrdersEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            try {
                val result = getCurrentUserUseCase()
                _state.value = _state.value.copy(
                    currentBuyerId = result.id
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

}
