package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.NotificationMessagesEnum
import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.notification.domain.entity.NotificationRequest
import com.delighted2wins.souqelkhorda.core.notification.domain.usecases.SendNotificationUseCase
import com.delighted2wins.souqelkhorda.features.chat.domain.usecase.DeleteOfferChatUseCase
import com.delighted2wins.souqelkhorda.features.history.domain.usecase.AddOrderToHistoryUseCase
import com.delighted2wins.souqelkhorda.features.history.domain.usecase.UpdateOrderStatusHistoryUseCase
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserDataByIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.DeleteOfferUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.GetOffersByOrderIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.UpdateOfferStatusUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase.GetOrderDetailsUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.SalesOrderDetailsEffect
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.SalesOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.SalesOrderDetailsState
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.DeleteCompanyOrderUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.DeleteMarketOrderUseCase
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
    private val addOrderToHistoryUseCase: AddOrderToHistoryUseCase,
    private val getOrderDetails: GetOrderDetailsUseCase,
    private val getOffersByOrderIdUseCase: GetOffersByOrderIdUseCase,
    private val getUserByIdUseCase: GetUserDataByIdUseCase,
    private val updateOrderStatusHistoryUseCase: UpdateOrderStatusHistoryUseCase,
    private val updateOfferStatusUseCase: UpdateOfferStatusUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase,
    private val deleteMarketOrderUseCase: DeleteMarketOrderUseCase,
    private val deleteOfferUseCase: DeleteOfferUseCase,
    private val deleteOfferChatUseCase: DeleteOfferChatUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SalesOrderDetailsState())
    val state: StateFlow<SalesOrderDetailsState> = _state

    private val _effect = MutableSharedFlow<SalesOrderDetailsEffect>()
    val effect = _effect.asSharedFlow()

    private val loadedUsers = mutableMapOf<String, MarketUser>()

    fun onIntent(intent: SalesOrderDetailsIntent) {
        when (intent) {
            is SalesOrderDetailsIntent.LoadOrderDetails -> loadOrderDetails(intent.orderId)
            is SalesOrderDetailsIntent.AcceptOffer -> acceptOffer(intent.offerId, intent.buyerId)
            is SalesOrderDetailsIntent.RejectOffer -> rejectOffer(intent.offerId, intent.buyerId)
            is SalesOrderDetailsIntent.CancelOffer -> cancelOffer(
                intent.orderId,
                intent.offerId,
                intent.buyerId
            )
            is SalesOrderDetailsIntent.CompleteOffer -> completeOffer(
                intent.orderId,
                intent.offerId,
                intent.buyerId
            )
            is SalesOrderDetailsIntent.ChatWithBuyer -> openChat(
                intent.orderId,
                intent.sellerId,
                intent.buyerId,
                intent.offerId
            )
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

                val accepted = offersWithUsers
                    .filter { it.first.status == OfferStatus.ACCEPTED }
                    .sortedWith(compareByDescending<Pair<Offer, MarketUser>> { it.first.date }
                        .thenByDescending { it.first.offerPrice })

                val pending = offersWithUsers
                    .filter { it.first.status == OfferStatus.PENDING }
                    .sortedWith(compareByDescending { it.first.date })


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

    private fun acceptOffer(offerId: String, buyerId: String) {
        viewModelScope.launch {
            try {
                val resultUpdateOffer = updateOfferStatusUseCase(offerId, OfferStatus.ACCEPTED)
                if (resultUpdateOffer){
                    sendNotificationUseCase(
                        request = NotificationRequest(
                            toUserId = buyerId,
                            message = NotificationMessagesEnum.OFFER_ACCEPTED.getMessage(),
                        )
                    )
                    _effect.emit( SalesOrderDetailsEffect.ShowError("Offer accepted successfully"))
                    reloadOffers()
                }else{
                    _effect.emit( SalesOrderDetailsEffect.ShowError("Failed to accept offer"))
                }
                reloadOffers()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to accept offer")
            }
        }
    }

    private fun rejectOffer(offerId: String, buyerId: String) {
        viewModelScope.launch {
            try {
                val resultDeleteOffer = deleteOfferUseCase(offerId)
                if (resultDeleteOffer){
                    sendNotificationUseCase(
                        request = NotificationRequest(
                            toUserId = buyerId,
                            message = NotificationMessagesEnum.OFFER_REJECTED.getMessage(),
                        )
                    )
                    _effect.emit( SalesOrderDetailsEffect.ShowError("Offer rejected successfully"))
                    reloadOffers()
                }else{
                    _effect.emit( SalesOrderDetailsEffect.ShowError("Failed to reject offer"))
                }
                reloadOffers()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to reject offer")
                emitEffect(SalesOrderDetailsEffect.ShowError(e.message ?: "Failed to reject offer"))
            }
        }
    }

    private fun cancelOffer(orderId: String, offerId: String, buyerId: String) {
        viewModelScope.launch {
            try {
                val resultUpdated = updateOrderStatusHistoryUseCase(orderId, buyerId, OrderStatus.CANCELLED)
                val resultDeleteChat = deleteOfferChatUseCase(orderId, offerId)
                val resultDeleteOffer = deleteOfferUseCase(offerId)
                if (resultDeleteOffer || resultDeleteChat || resultUpdated){
                    sendNotificationUseCase(
                        request = NotificationRequest(
                            toUserId = buyerId,
                            message = NotificationMessagesEnum.OFFER_DISCUSSION_CANCELED.getMessage(),
                        )
                    )
                    _effect.emit( SalesOrderDetailsEffect.ShowError("Offer canceled successfully"))
                    reloadOffers()
                }else{
                    _effect.emit( SalesOrderDetailsEffect.ShowError("Failed to cancel offer"))
                }
                reloadOffers()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to cancel offer")
                emitEffect(SalesOrderDetailsEffect.ShowError(e.message ?: "Failed to cancel offer"))
            }
        }
    }

    private fun completeOffer(orderId: String, offerId: String, buyerId: String) {
        viewModelScope.launch {
            try {
                val resultDeleteOrder = deleteMarketOrderUseCase(orderId)
                val resultUpdated = updateOrderStatusHistoryUseCase(orderId, buyerId, OrderStatus.COMPLETED)
                val resultDeleteChat = deleteOfferChatUseCase(orderId, offerId)
                val resultDeleteOffer = deleteOfferUseCase(offerId)
                if (resultDeleteOffer || resultDeleteChat || resultDeleteOrder || resultUpdated) {
                    sendNotificationUseCase(
                        request = NotificationRequest(
                            toUserId = buyerId,
                            message = NotificationMessagesEnum.OFFER_COMPLETED.getMessage(),
                        )
                    )
                    _effect.emit(SalesOrderDetailsEffect.ShowError("Offer completed successfully"))
                    reloadOffers()
                }else {
                    _effect.emit(SalesOrderDetailsEffect.ShowError("Failed to complete offer"))
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to complete offer")
                emitEffect(SalesOrderDetailsEffect.ShowError(e.message ?: "Failed to complete offer"))
            }
        }
    }

    private fun openChat(orderId: String, sellerId: String, buyerId: String, offerId: String) {
        viewModelScope.launch {
            try {
                _effect.emit(
                    SalesOrderDetailsEffect.NavigateToChat(
                        orderId = orderId,
                        sellerId = sellerId,
                        buyerId = buyerId,
                        offerId = offerId
                    )
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Failed to open chat")
                emitEffect(SalesOrderDetailsEffect.ShowError(e.message ?: "Failed to open chat"))
            }
        }
    }

    private fun emitEffect(effect: SalesOrderDetailsEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
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
