package com.delighted2wins.souqelkhorda.features.buyers.presentation.state

import com.delighted2wins.souqelkhorda.features.buyers.data.model.BuyerDto

sealed class BuyerState {
    data object Loading : BuyerState()
    data class RegisterSuccess(val buyerDto: BuyerDto) : BuyerState()
    data class SuccessLoading(val list: List<BuyerDto>) : BuyerState()
    data class Error(val errorMsg: String) : BuyerState()
    data object Idle : BuyerState()
}

