package com.delighted2wins.souqelkhorda.features.market.presentation.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetScrapOrdersUseCase
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketEffect
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketIntent
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getScrapOrdersUseCase: GetScrapOrdersUseCase
) : ViewModel() {

    var state by mutableStateOf(MarketState())
        private set

    private val _effect = MutableSharedFlow<MarketEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadOrders()
    }

    fun onIntent(intent: MarketIntent) {
        when (intent) {
            is MarketIntent.LoadScrapOrders -> loadOrders()

            is MarketIntent.Refresh -> {
                state = state.copy(isRefreshing = true)
                loadOrders()
                state = state.copy(isRefreshing = false)
            }

            is MarketIntent.SearchQueryChanged -> { state = state.copy(query = intent.query) }

            is MarketIntent.ClickOrder -> {
                viewModelScope.launch {
                    _effect.emit(MarketEffect.NavigateToOrderDetails(intent.order))
                }
            }

            is MarketIntent.SellNowClicked -> {
                viewModelScope.launch {
                    _effect.emit(MarketEffect.NavigateToSellNow)
                }
            }

        }
    }

    private fun loadOrders() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val orders = getScrapOrdersUseCase()
                state = state.copy(
                    isLoading = false,
                    successfulOrders = orders
                )
            } catch (e: Exception) {
                state = state.copy(isLoading = false)
                _effect.emit(MarketEffect.ShowError(e.message ?: "Unknown error"))
            }
        }
    }
}
