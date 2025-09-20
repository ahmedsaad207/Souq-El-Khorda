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
            MarketIntent.LoadScrapOrders -> loadOrders(showLoading = true)

            MarketIntent.Refresh -> loadOrders(showLoading = true, isRefreshing = true)

            is MarketIntent.SearchQueryChanged -> {
                state = state.copy(query = intent.query)
            }
            is MarketIntent.ClickOrder -> emitEffect(MarketEffect.NavigateToOrderDetails(intent.order))

            MarketIntent.SellNowClicked -> emitEffect(MarketEffect.NavigateToSellNow)
        }
    }

    private fun loadOrders(showLoading: Boolean = false, isRefreshing: Boolean = false) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isRefreshing = isRefreshing,
                isEmpty = false,
                error = null
            )
            try {
                val orders = getScrapOrdersUseCase()
                state = state.copy(
                    isLoading = false,
                    isRefreshing = false,
                    successfulOrders = orders,
                    isEmpty = orders.isEmpty()
                )
            } catch (e: Exception) {
                val errorMsg = e.message ?: "Network error"
                state = state.copy(
                    isLoading = false,
                    isRefreshing = false,
                    error = errorMsg
                )
                emitEffect(MarketEffect.ShowError("$errorMsg, please try again"))
            }
        }
    }

    private fun emitEffect(effect: MarketEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

}
