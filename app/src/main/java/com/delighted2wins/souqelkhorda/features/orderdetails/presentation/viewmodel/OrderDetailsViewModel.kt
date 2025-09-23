package com.delighted2wins.souqelkhorda.features.orderdetails.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.OrderSource
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Offer
import com.delighted2wins.souqelkhorda.features.market.domain.entities.MarketUser
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserDataByIdUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase.GetOrderDetailsUseCase
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OrderDetailsIntent
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OrderDetailsState
import com.delighted2wins.souqelkhorda.features.orderdetails.presentation.contract.OrderDetailsEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val getOrderDetails: GetOrderDetailsUseCase,
    private val fetchUserDataById : GetUserDataByIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<OrderDetailsState>(OrderDetailsState.Empty)
    val state: StateFlow<OrderDetailsState> = _state
    private val _orderOwner = MutableStateFlow<MarketUser?>(null)
    val orderOwner: StateFlow<MarketUser?> = _orderOwner
    private val _orderBuyer = MutableStateFlow<MarketUser?>(null)
    val orderBuyer: StateFlow<MarketUser?> = _orderBuyer

    private val _effect = MutableSharedFlow<OrderDetailsEffect>()
    val effect: MutableSharedFlow<OrderDetailsEffect> = _effect

    private lateinit var lastOrderId: String
    private lateinit var lastOwnerId: String
    private var lastBuyerId: String? = null
    private lateinit var lastSource: OrderSource
    private var lastSuccess: OrderDetailsState.Success? = null


    fun onIntent(intent: OrderDetailsIntent) {
        when (intent) {
            is OrderDetailsIntent.LoadOrderDetails -> {
                cacheParams(intent.orderId, intent.orderOwnerId, intent.buyerId, intent.source)
                loadOrder(isRefresh = false)
            }
            OrderDetailsIntent.Refresh -> {
                loadOrder(isRefresh = true)
            }
            OrderDetailsIntent.BackClicked -> {
                emitEffect(OrderDetailsEffect.NavigateBack)
            }
        }
    }

    private fun loadOrder(isRefresh: Boolean) {
        Log.d("OrderDetailsViewModel", "Loading order details for order ID: $lastOrderId")
        Log.d("OrderDetailsViewModel", "Loading order owner ID: $lastOwnerId")
        Log.d("OrderDetailsViewModel", "Loading order buyer ID: $lastBuyerId")
        Log.d("OrderDetailsViewModel", "--------------Loading order source: $lastSource")

        if (!::lastOrderId.isInitialized || !::lastOwnerId.isInitialized || !::lastSource.isInitialized) {
            Log.e("OrderDetailsViewModel", "Missing order parameters")
            _state.value = OrderDetailsState.Error("Missing order parameters")
            return
        }

        val orderId = lastOrderId
        val ownerId = lastOwnerId
        val buyerId = lastBuyerId
        val source = lastSource

        viewModelScope.launch {
            _state.value = if (isRefresh) OrderDetailsState.Refreshing else OrderDetailsState.Loading
            try {
                Log.d("OrderDetailsViewModel", "Fetching order details for order ID: $orderId")
                val order = getOrderDetails(orderId, source)
                Log.d("OrderDetailsViewModel", "------------------Fetched order details: $order")
                val successState = mapOrderToState(order, source, buyerId)
                if (successState is OrderDetailsState.Success) {
                    lastSuccess = successState
                }
                _state.value = successState
            } catch (e: Exception) {
                val msg = e.message ?: "Unknown error"

                if (lastSuccess != null) {
                    emitEffect(OrderDetailsEffect.ShowError(msg))
                    _state.value = lastSuccess!!
                } else {
                    _state.value = OrderDetailsState.Error(msg)
                }
            }
        }
    }

    private fun cacheParams(orderId: String, ownerId: String, buyerId: String?, source: OrderSource) {
        lastOrderId = orderId
        lastOwnerId = ownerId
        lastBuyerId = buyerId
        lastSource = source
    }

    private fun mapOrderToState(order: Order?, source: OrderSource, buyerId: String?): OrderDetailsState {
        return if (order == null) {
            OrderDetailsState.Empty
        } else {
            when (source) {
                OrderSource.MARKET -> OrderDetailsState.Success.Market(order)
                OrderSource.COMPANY -> OrderDetailsState.Success.Company(order)
                OrderSource.SALES -> OrderDetailsState.Success.Sales(order)
                OrderSource.OFFERS -> {
                    val buyerOffer: Offer? = order.offers.find { it.buyerId == buyerId }
                    OrderDetailsState.Success.Offers(order, buyerOffer)
                }
                else -> OrderDetailsState.Error("Unknown source")
            }
        }
    }

    private fun emitEffect(effect: OrderDetailsEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    val cachedSuccess: OrderDetailsState.Success?
        get() = lastSuccess

    fun getUserData(userId: String) {
        if (userId.isEmpty()) {
            _orderOwner.value = null
            return
        }
        viewModelScope.launch {
            try {
                val user = fetchUserDataById(userId)
                _orderOwner.value = user
                Log.d("OrderDetailsViewModel", "Fetched user data: $user")
            } catch (e: Exception) {
                Log.e("OrderDetailsViewModel", "Error fetching user data: ${e.message}")
                _orderOwner.value = MarketUser(id = "", name = "Unknown", location = "Unknown")
            }
        }
    }

}
