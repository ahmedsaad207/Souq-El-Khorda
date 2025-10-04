package com.delighted2wins.souqelkhorda.features.buyers.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.AuthMsgEnum
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.GetCashUserCase
import com.delighted2wins.souqelkhorda.features.buyers.data.model.toBuyerDto
import com.delighted2wins.souqelkhorda.features.buyers.domain.use_case.GetBuyersCase
import com.delighted2wins.souqelkhorda.features.buyers.domain.use_case.IsBuyersCase
import com.delighted2wins.souqelkhorda.features.buyers.domain.use_case.RegisterBuyersCase
import com.delighted2wins.souqelkhorda.features.buyers.presentation.contract.BuyerIntent
import com.delighted2wins.souqelkhorda.features.buyers.presentation.contract.BuyerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyerViewModel @Inject constructor(
    val registerBuyersCase: RegisterBuyersCase,
    val getBuyersCase: GetBuyersCase,
    val isBuyersCase: IsBuyersCase,
    val getUserCase: GetCashUserCase
) : ViewModel() {
    private val _registerState = MutableStateFlow<BuyerState>(BuyerState.Idle)
    val registerState = _registerState.asStateFlow()

    private val _nearestBuyers = MutableStateFlow<BuyerState>(BuyerState.Loading)
    val nearestBuyers = _nearestBuyers.asStateFlow()
    private val _isBuyerState = MutableStateFlow<Boolean>(false)
    val isBuyerState = _isBuyerState.asStateFlow()
    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()
    private val _intents = MutableSharedFlow<BuyerIntent>()


    init {
        viewModelScope.launch {
            _intents.collect { intent ->
                handleIntent(intent)
            }
        }
    }

    fun processIntent(intent: BuyerIntent) {
        viewModelScope.launch {
            _intents.emit(intent)
        }
    }

    private fun handleIntent(intent: BuyerIntent) {
        when (intent) {
            is BuyerIntent.GetAllBuyerIntent -> getNearstBuyers()
            is BuyerIntent.RegisterBuyer -> registerBuyer(intent.latitude, intent.longitude, intent.scrapTypes)
            BuyerIntent.CheckIfBuyer -> isBuyer()
        }
    }
    private fun registerBuyer(latitude: Double, longitude: Double, scrapTypes: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (latitude == 0.0 || longitude == 0.0) {
                _message.emit(AuthMsgEnum.LOCATIONEMPTY.getMsg())
                return@launch
            }
            if (scrapTypes.isEmpty()) {
                _message.emit(AuthMsgEnum.SCRAPLISTEMPTY.getMsg())
                return@launch
            }
            registerBuyersCase(
                getUserCase().toBuyerDto(
                    latitude,
                    longitude,
                    scrapTypes
                )
            ).catch {
                _message.emit(AuthMsgEnum.UNAUTHORIZED.getMsg())
            }.collect { state ->
                _registerState.emit(state)
                when (state) {
                    is BuyerState.RegisterSuccess -> {
                        val user = state.buyerDto
                        _message.emit(AuthMsgEnum.SIGNUPSUCCESS.getMsg())
                    }

                    is BuyerState.Error -> {
                        _message.emit(AuthMsgEnum.UNAUTHORIZED.getMsg())
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getNearstBuyers() {
        viewModelScope.launch(Dispatchers.IO) {
            getBuyersCase().catch {
                _message.emit(AuthMsgEnum.UNAUTHORIZED.getMsg())
            }.collect { state ->
                when (state) {
                    is BuyerState.SuccessLoading -> {
                        val user = getUserCase()
                        val filteredBuyers = state.list.filter { buyer ->
                            buyer.governorate == user.governorate && buyer.buyerID != user.id
                        }
                        _nearestBuyers.emit(BuyerState.SuccessLoading(filteredBuyers))
                    }

                    is BuyerState.Error -> {
                        _nearestBuyers.emit(state)
                        _message.emit(AuthMsgEnum.UNAUTHORIZED.getMsg())
                    }

                    else -> {
                        _nearestBuyers.emit(state)
                    }
                }
            }
        }
    }

    private fun isBuyer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isBuyerState.value = isBuyersCase(getUserCase().id)
            } catch (e: Exception) {
                _isBuyerState.value = false
                _message.emit(AuthMsgEnum.UNAUTHORIZED.getMsg())
            }
        }
    }
}