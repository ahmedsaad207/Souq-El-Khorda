package com.delighted2wins.souqelkhorda.features.market.presentation.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetMarketOrdersUseCase
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserForMarketUseCase
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
    private val getMarketOrdersUseCase: GetMarketOrdersUseCase,
    private val getMarketUserUseCase: GetUserForMarketUseCase
) : ViewModel() {

    var state by mutableStateOf(MarketState())
        private set

    private val loadedUsers = mutableMapOf<String, MarketUser>()

    private val _effect = MutableSharedFlow<MarketEffect>()
    val effect = _effect.asSharedFlow()

    init {
        loadOrders()
    }

    fun onIntent(intent: MarketIntent) {
        when (intent) {
            MarketIntent.LoadScrapOrders -> loadOrders()

            MarketIntent.Refresh -> loadOrders(isRefreshing = true)

            is MarketIntent.SearchQueryChanged -> {
                state = state.copy(query = intent.query)
            }
            is MarketIntent.NavigateToOrderDetails -> emitEffect(
                MarketEffect.NavigateToOrderDetails(
                    intent.order,
                    intent.user
                )
            )

            MarketIntent.SellNowClicked -> emitEffect(MarketEffect.NavigateToSellNow)
        }
    }

    fun getUserData(userId: String, onLoaded: (MarketUser) -> Unit) {
        val cachedUser = loadedUsers[userId]
        if (cachedUser != null) {
            onLoaded(cachedUser)
            return
        }

        viewModelScope.launch {
            try {
                val user = getMarketUserUseCase(userId)
                loadedUsers[userId] = user
                onLoaded(user)
            } catch (e: Exception) {
                onLoaded(MarketUser(id = 0, name = "User $userId", location = "Unknown"))
            }
        }
    }


    private fun loadOrders(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isRefreshing = isRefreshing,
                isEmpty = false,
                error = null
            )
            try {
                val orders = getMarketOrdersUseCase()
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
