package com.delighted2wins.souqelkhorda.features.myorders.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.features.myorders.domain.usecase.LoadOffersUseCase
import com.delighted2wins.souqelkhorda.features.myorders.domain.usecase.LoadCompanyOrdersUseCase
import com.delighted2wins.souqelkhorda.features.myorders.domain.usecase.LoadSellsUseCase
import com.delighted2wins.souqelkhorda.features.myorders.presentation.contract.MyOrdersIntents
import com.delighted2wins.souqelkhorda.features.myorders.presentation.contract.MyOrdersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    private val loadCompanyOrdersUseCase: LoadCompanyOrdersUseCase,
    private val loadSellsUseCase: LoadSellsUseCase,
    private val loadOffersUseCase: LoadOffersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MyOrdersState())
    val state: StateFlow<MyOrdersState> = _state

    fun onIntent(intent: MyOrdersIntents) {
        when (intent) {
            is MyOrdersIntents.LoadSaleOrders -> loadSaleOrders()
            is MyOrdersIntents.LoadSells -> loadSells()
            is MyOrdersIntents.LoadOffers -> loadOffers()
        }
    }

    private fun loadSaleOrders() {
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
}
