package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.OfferStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetCurrentUserUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.GetOffersByOrderIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.UpdateOfferStatusUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase.GetOrderDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesOrderDetailsViewModel @Inject constructor(
    private val getOrderDetails: GetOrderDetailsUseCase,
    private val getOffersByOrderIdUseCase: GetOffersByOrderIdUseCase,
    private val updateOfferStatusUseCase: UpdateOfferStatusUseCase,
) : ViewModel() {

    private val _orderState = MutableStateFlow<Order?>(null)
    val orderState: StateFlow<Order?> = _orderState

    private val _offersState = MutableStateFlow<List<Offer>>(emptyList())
    val offersState: StateFlow<List<Offer>> = _offersState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun loadOrderDetails(orderId: String) {
        viewModelScope.launch {
            try {
                val order = getOrderDetails(orderId, OrderSource.MARKET)
                _orderState.value = order

                val allOffers = getOffersByOrderIdUseCase(orderId)
                Log.d("SalesOrderDetailsViewModel", "All offers: $allOffers")

                _offersState.value = allOffers.filter { it.status == OfferStatus.PENDING }
            } catch (e: Exception) {
                _errorState.value = e.message ?: "Unknown error"
            }
        }
    }

    fun acceptOffer(offerId: String) {
        viewModelScope.launch {
            try {
                updateOfferStatusUseCase(offerId, OfferStatus.ACCEPTED)

                _offersState.value = _offersState.value.map { offer ->
                    if (offer.offerId == offerId) offer.copy(status = OfferStatus.ACCEPTED)
                    else offer
                }

                _orderState.value = _orderState.value?.copy(status = OrderStatus.COMPLETED)
            } catch (e: Exception) {
                _errorState.value = e.message ?: "Failed to accept offer"
            }
        }
    }

    fun rejectOffer(offerId: String) {
        viewModelScope.launch {
            try {
                updateOfferStatusUseCase(offerId, OfferStatus.REJECTED)

                _offersState.value = _offersState.value.map { offer ->
                    if (offer.offerId == offerId) offer.copy(status = OfferStatus.REJECTED)
                    else offer
                }
            } catch (e: Exception) {
                _errorState.value = e.message ?: "Failed to reject offer"
            }
        }
    }

}
