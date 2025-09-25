package com.delighted2wins.souqelkhorda.features.market.presentation.screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetCurrentUserUseCase
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetMarketOrdersUseCase
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserDataByIdUseCase
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketEffect
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketIntent
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketState
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.MakeOfferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val fetchMarketOrders: GetMarketOrdersUseCase,
    private val getUserByIdUseCase: GetUserDataByIdUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val makeOfferUseCase: MakeOfferUseCase
) : ViewModel() {

    var state by mutableStateOf(MarketState())
        private set

    private var _currentUser by mutableStateOf<MarketUser?>(null)
    val currentUser: MarketUser? get() = _currentUser

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
                    intent.orderId,
                    intent.orderOwnerId
                )
            )

            is MarketIntent.MakeOffer -> {
                makeOffer(intent.offer)
            }

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
                val user = getUserByIdUseCase(userId)
                loadedUsers[userId] = user
                onLoaded(user)
            } catch (e: Exception) {
                onLoaded(MarketUser(id = "", name = "User $userId", location = "Unknown"))
            }
        }
    }

    suspend fun loadCurrentUser() {
        _currentUser = try {
            getCurrentUserUseCase()
        } catch (e: Exception) {
            Log.e("MarketViewModel", "Error loading current user", e)
            MarketUser(id = "", name = "User", location = "Unknown")
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
                val orders =  fetchMarketOrders().filter { it.status == OrderStatus.PENDING }
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

    private fun makeOffer(offer: Offer) {
        viewModelScope.launch {
            state = state.copy(isSubmitting = true)
            try {
                val offerId = makeOfferUseCase(offer)
                if (offerId.isNotEmpty()) {
                    emitEffect(MarketEffect.ShowSuccess("Offer made successfully"))
                }
            } catch (e: Exception) {
                val errorMsg = e.message ?: "Network error"
                emitEffect(MarketEffect.ShowError(errorMsg))
            } finally {
                state = state.copy(isSubmitting = false)
            }
        }
    }


}
