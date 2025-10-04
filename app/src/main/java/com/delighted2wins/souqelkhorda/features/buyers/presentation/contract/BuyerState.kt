package com.delighted2wins.souqelkhorda.features.buyers.presentation.contract

import com.delighted2wins.souqelkhorda.features.buyers.data.model.BuyerDto


sealed class BuyerIntent {
    data object GetAllBuyerIntent: BuyerIntent()
    data class RegisterBuyer(val latitude: Double, val longitude: Double, val scrapTypes: List<String>) : BuyerIntent()
    data object CheckIfBuyer : BuyerIntent()

}

sealed class BuyerState {
    data object Loading : BuyerState()
    data class RegisterSuccess(val buyerDto: BuyerDto) : BuyerState()
    data class SuccessLoading(val list: List<BuyerDto>) : BuyerState()
    data class Error(val errorMsg: String) : BuyerState()
    data object Idle : BuyerState()
}

