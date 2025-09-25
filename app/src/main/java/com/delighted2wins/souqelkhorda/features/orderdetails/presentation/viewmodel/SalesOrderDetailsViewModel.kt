package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserDataByIdUseCase
import com.delighted2wins.souqelkhorda.features.market.presentation.contract.MarketEffect
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.GetOffersByOrderIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.UpdateOfferStatusUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase.GetOrderDetailsUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.SalesOrderDetailsEffect
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.SalesOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.SalesOrderDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesOrderDetailsViewModel @Inject constructor(
    private val getOrderDetails: GetOrderDetailsUseCase,
    private val getOffersByOrderIdUseCase: GetOffersByOrderIdUseCase,
    private val updateOfferStatusUseCase: UpdateOfferStatusUseCase,
    private val getUserByIdUseCase: GetUserDataByIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SalesOrderDetailsState())
    val state: StateFlow<SalesOrderDetailsState> = _state

    private val _effect = MutableSharedFlow<SalesOrderDetailsEffect>()
    val effect = _effect.asSharedFlow()

    private val loadedUsers = mutableMapOf<String, MarketUser>()

    fun onIntent(intent: SalesOrderDetailsIntent) {
        when (intent) {
            is SalesOrderDetailsIntent.LoadOrderDetails -> loadOrderDetails(intent.orderId)
            is SalesOrderDetailsIntent.AcceptOffer -> acceptOffer(intent.offerId)
            is SalesOrderDetailsIntent.RejectOffer -> rejectOffer(intent.offerId)
            is SalesOrderDetailsIntent.CancelOffer -> cancelOffer(intent.offerId)
            is SalesOrderDetailsIntent.CompleteOffer -> completeOffer(intent.offerId)
            is SalesOrderDetailsIntent.ChatWithBuyer -> openChat(intent.sellerId, intent.buyerId, intent.orderId)
        }
    }

    private fun loadOrderDetails(orderId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val order = getOrderDetails(orderId, OrderSource.MARKET)
                val offers = getOffersByOrderIdUseCase(orderId)

                val offersWithUsers = offers.map { offer ->
                    async {
                        val user = getUserData(offer.buyerId)
                        if (user != null) offer to user else null
                    }
                }.awaitAll().filterNotNull()

                val accepted = offersWithUsers.filter { it.first.status == OfferStatus.ACCEPTED }
                val pending = offersWithUsers.filter { it.first.status == OfferStatus.PENDING }

                _state.value = _state.value.copy(
                    order = order,
                    acceptedOffers = accepted,
                    pendingOffers = pending,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Unknown error",
                    isLoading = false
                )
            }
        }
    }

    private fun acceptOffer(offerId: String) {
        viewModelScope.launch {
            try {
                // call send notification to buyer (msg: offer accepted by seller)
                updateOfferStatusUseCase(offerId, OfferStatus.ACCEPTED)
                reloadOffers()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to accept offer")
            }
        }
    }

    private fun rejectOffer(offerId: String) {
        viewModelScope.launch {
            try {
                // call send notification to buyer (msg: offer rejected by seller)
                updateOfferStatusUseCase(offerId, OfferStatus.REJECTED)
                reloadOffers()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to reject offer")
            }
        }
    }

    private fun cancelOffer(offerId: String) {
        viewModelScope.launch {
            try {
                // call send notification to buyer (msg: offer canceled by seller)
                updateOfferStatusUseCase(offerId, OfferStatus.REJECTED)
                reloadOffers()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to cancel offer")
            }
        }
    }

    private fun completeOffer(offerId: String) {
        viewModelScope.launch {
            try {
                 // call send notification to buyer (msg: offer completed by seller)
                // handel order status to completed and history

                 // updateBuyerHistoryStatusUseCase(buyerId, OrderStatus.COMPLETED)
                // updateOrderStatusUseCase(orderId, OrderStatus.COMPLETED)
               // updateOfferStatusUseCase(offerId, OfferStatus.COMPLETED)

                reloadOffers()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to complete offer")
            }
        }
    }

    private fun openChat(sellerId: String, buyerId: String, orderId: String) {
        viewModelScope.launch {
            try {
                _effect.emit(
                    SalesOrderDetailsEffect.NavigateToChat(
                        sellerId = sellerId,
                        buyerId = buyerId,
                        orderId = orderId
                    )
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to open chat")
            }
        }
    }


    private suspend fun getUserData(userId: String): MarketUser? {
        return loadedUsers[userId] ?: try {
            getUserByIdUseCase(userId).also { loadedUsers[userId] = it }
        } catch (e: Exception) {
            null
        }
    }

    private fun reloadOffers() {
        _state.value.order?.orderId?.let { orderId ->
            viewModelScope.launch {
                try {
                    val offers = getOffersByOrderIdUseCase(orderId)
                    val offersWithUsers = offers.map { offer ->
                        async {
                            val user = getUserData(offer.buyerId)
                            if (user != null) offer to user else null
                        }
                    }.awaitAll().filterNotNull()

                    val accepted = offersWithUsers.filter { it.first.status == OfferStatus.ACCEPTED }.sortedByDescending { it.first.date }
                    val pending = offersWithUsers.filter { it.first.status == OfferStatus.PENDING }.sortedByDescending { it.first.date }

                    _state.value = _state.value.copy(
                        acceptedOffers = accepted,
                        pendingOffers = pending
                    )
                } catch (e: Exception) {
                    _state.value = _state.value.copy(error = e.message ?: "Failed to reload offers")
                }
            }
        }
    }
}
