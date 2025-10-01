package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.NotificationMessagesEnum
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.notification.domain.entity.NotificationRequest
import com.delighted2wins.souqelkhorda.core.notification.domain.usecases.SendNotificationUseCase
import com.delighted2wins.souqelkhorda.features.history.domain.usecase.AddOrderOfferToHistoryUseCase
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetCurrentUserUseCase
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserDataByIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.MakeOfferUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase.GetOrderDetailsUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.MarketOrderDetailsEffect
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.MarketOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.MarketOrderDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketOrderDetailsViewModel @Inject constructor(
    private val getOrderDetails: GetOrderDetailsUseCase,
    private val getUserDataById: GetUserDataByIdUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val makeOfferUseCase: MakeOfferUseCase,
    private val addOrderOfferToHistoryUseCase: AddOrderOfferToHistoryUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MarketOrderDetailsState>(MarketOrderDetailsState.Empty)
    val state: StateFlow<MarketOrderDetailsState> = _state
    private val _effect = MutableSharedFlow<MarketOrderDetailsEffect>()
    val effect = _effect.asSharedFlow()

    val _currentUser = MutableStateFlow<MarketUser?>(null)
    val currentUser: StateFlow<MarketUser?> = _currentUser

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
            is MarketOrderDetailsIntent.MakeOffer -> {
                makeOffer(intent.order,intent.offer, intent.sellerId)
            }
        }
    }

    private fun loadOrder(orderId: String, orderOwnerId: String) {
        viewModelScope.launch {
            _state.value = MarketOrderDetailsState.Loading
            try {
                val order = getOrderDetails(orderId, OrderSource.MARKET)
                val owner = getUserDataById(order?.userId ?: orderOwnerId)
                _currentUser.value = getCurrentUserUseCase()
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

    private fun makeOffer(order: Order, offer: Offer, sellerId: String) {
        viewModelScope.launch {
            _state.value = when (val current = _state.value) {
                is MarketOrderDetailsState.Success ->
                    current.copy(isSubmitting = true)
                else -> current
            }

            try {
                val offerId = makeOfferUseCase(offer)
                val resultAddOrderToHistory =
                    addOrderOfferToHistoryUseCase(order, getCurrentUserUseCase().id)
                if (offerId.isNotEmpty() && resultAddOrderToHistory) {
                    launch {
                        try {
                            sendNotificationUseCase(
                                request = NotificationRequest(
                                    toUserId = sellerId,
                                    message = NotificationMessagesEnum.OFFER_SENT_PENDING.getMessage()
                                )
                            )
                        } catch (e: Exception) {
                            
                        }
                    }
                    emitEffect(MarketOrderDetailsEffect.ShowSuccess("Offer made successfully"))                }
            } catch (e: Exception) {
                val errorMsg = e.message ?: "Network error"
                emitEffect(MarketOrderDetailsEffect.ShowError(errorMsg))
            } finally {
                _state.value = when (val current = _state.value) {
                    is MarketOrderDetailsState.Success ->
                        current.copy(isSubmitting = false)
                    else -> current
                }
            }
        }
    }

    private fun emitEffect(effect: MarketOrderDetailsEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}
