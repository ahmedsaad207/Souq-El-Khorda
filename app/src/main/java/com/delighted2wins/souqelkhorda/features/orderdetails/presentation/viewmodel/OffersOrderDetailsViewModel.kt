package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.NotificationMessagesEnum
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.notification.domain.entity.NotificationRequest
import com.delighted2wins.souqelkhorda.core.notification.domain.usecases.SendNotificationUseCase
import com.delighted2wins.souqelkhorda.features.chat.domain.usecase.DeleteOfferChatUseCase
import com.delighted2wins.souqelkhorda.features.history.domain.usecase.UpdateOrderStatusHistoryUseCase
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetCurrentUserUseCase
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserDataByIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.DeleteOfferUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.GetOffersByOrderIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.MakeOfferUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase.GetOrderDetailsUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsEffect
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsState
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.DeleteMarketOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersOrderDetailsViewModel @Inject constructor(
    private val updateOrderStatusHistoryUseCase: UpdateOrderStatusHistoryUseCase,
    private val getOrderDetails: GetOrderDetailsUseCase,
    private val getOffersByOrderIdUseCase: GetOffersByOrderIdUseCase,
    private val makeOfferUseCase: MakeOfferUseCase,
    private val deleteOfferUseCase: DeleteOfferUseCase,
    private val getUserByIdUseCase: GetUserDataByIdUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase,
    private val deleteMarketOrderUseCase: DeleteMarketOrderUseCase,
    private val deleteOfferChatUseCase: DeleteOfferChatUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(OffersOrderDetailsState())
    val state: StateFlow<OffersOrderDetailsState> = _state

    private val _effect = MutableSharedFlow<OffersOrderDetailsEffect>()
    val effect = _effect.asSharedFlow()

    fun onIntent(intent: OffersOrderDetailsIntent) {
        when (intent) {
            is OffersOrderDetailsIntent.LoadOrderDetails -> loadOrderDetails(intent.orderId)
            is OffersOrderDetailsIntent.ChatWithSeller -> openChat(
                intent.orderId,
                intent.sellerId,
                intent.buyerId,
                intent.offerId
            )

            is OffersOrderDetailsIntent.UpdateOffer -> updateOffer(
                intent.offerId,
                intent.newPrice,
                intent.sellerId
            )

            is OffersOrderDetailsIntent.CancelOffer -> cancelOffer(
                intent.orderId,
                intent.offerId,
                intent.sellerId
            )

            is OffersOrderDetailsIntent.MarkAsReceived -> markAsReceived(
                intent.orderId,
                intent.offerId,
                intent.sellerId
            )
        }
    }

    private fun loadOrderDetails(orderId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val order = getOrderDetails(orderId, OrderSource.MARKET)
                val offers = getOffersByOrderIdUseCase(orderId)
                val myOffer = offers.find { it.buyerId == getCurrentUserUseCase().id }

                val sellerUser = order?.userId?.let { getUserByIdUseCase(it) } ?: return@launch

                _state.value = _state.value.copy(
                    isLoading = false,
                    order = order,
                    buyerOffer = myOffer?.let { it to sellerUser }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun updateOffer(offerId: String, price: String, sellerId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSubmitting = true)
            try {
                val currentOffer = _state.value.buyerOffer?.first ?: return@launch
                val result = makeOfferUseCase(
                    offer = currentOffer.copy(
                        offerId = offerId,
                        offerPrice = price.toInt()
                    )
                )
                if (result.isNotEmpty()) {
                    launch {
                        try {
                            sendNotificationUseCase(
                                request = NotificationRequest(
                                    toUserId = sellerId,
                                    message = NotificationMessagesEnum.BUYER_UPDATED_OFFER.getMessage(),
                                )
                            )
                        } catch (e: Exception) {
                            Log.e("OffersViewModel", "Notification failed: ${e.message}")
                        }
                    }
                    _effect.emit(OffersOrderDetailsEffect.ShowSuccess("Offer updated successfully"))
                    _state.value.order?.orderId?.let { reloadOffer(it) }
                } else {
                    _effect.emit(OffersOrderDetailsEffect.ShowError("Failed to update offer"))
                }
            } catch (e: Exception) {
                _effect.emit(
                    OffersOrderDetailsEffect.ShowError(
                        e.message ?: "Failed to update offer"
                    )
                )
            } finally {
                _state.value = _state.value.copy(isSubmitting = false)
            }
        }
    }

    private fun cancelOffer(orderId: String, offerId: String, sellerId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSubmitting = true)
            try {
                val resultUpdatedHistoryBuyer = updateOrderStatusHistoryUseCase(
                    orderId,
                    getCurrentUserUseCase().id,
                    "offers",
                    OrderStatus.CANCELLED
                )
                val resultDeleteOrder = deleteOfferChatUseCase(orderId, offerId)
                val resultDeleteOffer = deleteOfferUseCase(offerId)
                Log.e("OffersViewModel", "resultDeleteOffer: $resultDeleteOffer")
                Log.e("OffersViewModel", "resultDeleteOrder: $resultDeleteOrder")
                Log.e("OffersViewModel", "resultUpdatedHistoryBuyer: $resultUpdatedHistoryBuyer")
                if (resultDeleteOffer && resultDeleteOrder && resultUpdatedHistoryBuyer) {
                    launch {
                        try {
                            sendNotificationUseCase(
                                request = NotificationRequest(
                                    toUserId = sellerId,
                                    message = NotificationMessagesEnum.BUYER_CANCELED_OFFER.getMessage(),
                                )
                            )
                        } catch (e: Exception) {
                            Log.e("OffersViewModel", "Notification failed: ${e.message}")
                        }
                    }
                    _effect.emit(OffersOrderDetailsEffect.ShowSuccess("Offer canceled successfully"))
                    _state.value = _state.value.copy(buyerOffer = null)
                    reloadOffer(orderId)
                } else {
                    _effect.emit(OffersOrderDetailsEffect.ShowError("Failed to cancel offer"))
                }
            } catch (e: Exception) {
                _effect.emit(
                    OffersOrderDetailsEffect.ShowError(
                        e.message ?: "Failed to cancel offer"
                    )
                )
            } finally {
                _state.value = _state.value.copy(isSubmitting = false)
            }
        }
    }

    private fun markAsReceived(orderId: String, offerId: String, sellerId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSubmitting = true)
            try {
                val resultUpdatedHistoryBuyer = updateOrderStatusHistoryUseCase(
                    orderId,
                    getCurrentUserUseCase().id,
                    "offers",
                    OrderStatus.COMPLETED
                )
                val resultUpdatedHistorySeller = updateOrderStatusHistoryUseCase(
                    orderId,
                    sellerId,
                    "orders",
                    OrderStatus.COMPLETED
                )
                val resultDeleteOrder = deleteMarketOrderUseCase(orderId)
                val resultDeleteOffer = deleteOfferUseCase(offerId)
                val resultDeleteOfferChat = deleteOfferChatUseCase(orderId, offerId)
                if (resultDeleteOffer && resultDeleteOfferChat && resultDeleteOrder && resultUpdatedHistorySeller && resultUpdatedHistoryBuyer) {
                    launch {
                        try {
                            sendNotificationUseCase(
                                request = NotificationRequest(
                                    toUserId = sellerId,
                                    message = NotificationMessagesEnum.BUYER_MARKED_RECEIVED.getMessage(),
                                )
                            )
                        } catch (e: Exception) {
                            Log.e("OffersViewModel", "Notification failed: ${e.message}")
                        }
                    }
                    _effect.emit(OffersOrderDetailsEffect.ShowSuccess("Order marked as received"))
                    _state.value = _state.value.copy(buyerOffer = null)
                    reloadOffer(orderId)
                } else {
                    _effect.emit(OffersOrderDetailsEffect.ShowError("Failed to mark as received"))
                }
            } catch (e: Exception) {
                _effect.emit(
                    OffersOrderDetailsEffect.ShowError(
                        e.message ?: "Failed to mark as received"
                    )
                )
            } finally {
                _state.value = _state.value.copy(isSubmitting = false)
            }
        }
    }


    private fun openChat(orderId: String, sellerId: String, buyerId: String, offerId: String) {
        viewModelScope.launch {
            try {
                _effect.emit(
                    OffersOrderDetailsEffect.NavigateToChat(
                        orderId = orderId,
                        sellerId = sellerId,
                        buyerId = buyerId,
                        offerId = offerId
                    )
                )
            } catch (e: Exception) {
                _effect.emit(OffersOrderDetailsEffect.ShowError(e.message ?: "Failed to open chat"))
            }
        }
    }

    private fun reloadOffer(orderId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val refreshedOrder = getOrderDetails(orderId, OrderSource.MARKET)
                val offers = getOffersByOrderIdUseCase(orderId)
                val myOffer = offers.find { it.buyerId == getCurrentUserUseCase().id }
                val sellerUser =
                    refreshedOrder?.userId?.let { getUserByIdUseCase(it) } ?: return@launch

                _state.value = _state.value.copy(
                    isLoading = false,
                    order = refreshedOrder,
                    buyerOffer = myOffer?.let { it to sellerUser }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
                _effect.emit(
                    OffersOrderDetailsEffect.ShowError(
                        e.message ?: "Failed to refresh offer"
                    )
                )
            }
        }
    }

}
