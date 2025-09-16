package com.delighted2wins.souqelkhorda.features.market.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.delighted2wins.souqelkhorda.features.sale.presentation.SaleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel@Inject constructor(

): ViewModel() {
    private var _state = MutableStateFlow(SaleState())
    val state = _state

}