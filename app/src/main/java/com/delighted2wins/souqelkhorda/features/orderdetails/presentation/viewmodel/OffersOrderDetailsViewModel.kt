package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetCurrentUserUseCase
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserDataByIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.DeleteOfferUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.GetOffersByOrderIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.MakeOfferUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.UpdateOfferStatusUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase.GetOrderDetailsUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsState
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OffersOrderDetailsEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersOrderDetailsViewModel @Inject constructor(
    private val getOrderDetails: GetOrderDetailsUseCase,
    private val getOffersByOrderIdUseCase: GetOffersByOrderIdUseCase,
    private val makeOfferUseCase: MakeOfferUseCase,
    private val updateOfferStatusUseCase: UpdateOfferStatusUseCase,
    private val deleteOfferUseCase: DeleteOfferUseCase,
    private val getUserByIdUseCase: GetUserDataByIdUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
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
            is OffersOrderDetailsIntent.UpdateOffer ->  updateOffer(intent.offerId, intent.newPrice)
            is OffersOrderDetailsIntent.CancelOffer -> cancelOffer(intent.offerId)
            is OffersOrderDetailsIntent.MarkAsReceived -> markAsReceived(intent.offerId)
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

    private fun updateOffer(offerId: String, price: String) {
        viewModelScope.launch {
            try {
                val currentOffer = _state.value.buyerOffer?.first ?: return@launch
                val result = makeOfferUseCase(
                    offer = currentOffer.copy(offerId = offerId, offerPrice = price.toInt())
                )
                if (result.isNotEmpty()) {
                    _effect.emit(OffersOrderDetailsEffect.ShowSuccess("Offer updated successfully"))
                    reloadOffer(offerId)
                }else{
                    _effect.emit(OffersOrderDetailsEffect.ShowError("Failed to update offer"))
                }
            }catch (e : Exception){
                _effect.emit(OffersOrderDetailsEffect.ShowError(e.message ?: "Failed to update offer"))
            }
        }
    }
    private fun cancelOffer(offerId: String) {
        viewModelScope.launch {
            try {
                val result = deleteOfferUseCase(offerId)
                if (result) {
                    _effect.emit(OffersOrderDetailsEffect.ShowSuccess("Offer canceled successfully"))
                    _state.value = _state.value.copy(buyerOffer = null)
                } else {
                    _effect.emit(OffersOrderDetailsEffect.ShowError("Failed to cancel offer"))
                }
            } catch (e: Exception) {
                _effect.emit(OffersOrderDetailsEffect.ShowError(e.message ?: "Failed to cancel offer"))
            }
        }
    }

    private fun markAsReceived(offerId: String) {
        viewModelScope.launch {
            try {
//                val result = updateOfferStatusUseCase(offerId, OfferStatus.COMPLETED)
//                if (result) {
//                    _effect.emit(BuyerOrderDetailsEffect.ShowSuccess("Order marked as received"))
//                    reloadOffer(offerId)
//                } else {
//                    _effect.emit(BuyerOrderDetailsEffect.ShowError("Failed to mark as received"))
//                }
            } catch (e: Exception) {
                _effect.emit(OffersOrderDetailsEffect.ShowError(e.message ?: "Failed to mark as received"))
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
            }catch (e : Exception){
                _effect.emit(OffersOrderDetailsEffect.ShowError(e.message ?: "Failed to open chat"))
            }
        }
    }

    private fun reloadOffer(offerId: String) {
        viewModelScope.launch {
            try {
                val order = _state.value.order ?: return@launch
                val offers = getOffersByOrderIdUseCase(order.orderId)
                val refreshed = offers.find { it.offerId == offerId }

                val sellerUser = order.userId?.let { getUserByIdUseCase(it) } ?: return@launch

                _state.value = _state.value.copy(
                    buyerOffer = refreshed?.let { it to sellerUser }
                )
            } catch (e: Exception) {
                _effect.emit(OffersOrderDetailsEffect.ShowError(e.message ?: "Failed to refresh offer"))
            }
        }
    }
}
