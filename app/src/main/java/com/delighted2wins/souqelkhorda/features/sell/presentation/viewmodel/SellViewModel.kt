package com.delighted2wins.souqelkhorda.features.sell.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.features.additem.domain.usecase.DeleteAllScrapsUseCase
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.GetScrapesUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.SendOrderUseCase
import com.delighted2wins.souqelkhorda.features.sell.presentation.SaleIntent
import com.delighted2wins.souqelkhorda.features.sell.presentation.SaleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellViewModel @Inject constructor(
    private val getScrapesUseCase: GetScrapesUseCase,
    private val deleteAllScrapsUseCase: DeleteAllScrapsUseCase,
    private val sendOrderUseCase: SendOrderUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(SaleState())
    val state = _state.asStateFlow()

    init {
        loadScraps()
    }

    private fun loadScraps() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        getScrapesUseCase()
            .catch { _state.value = _state.value.copy(isLoading = false, err = it.message) }
            .collect {
                _state.value = _state.value.copy(isLoading = false, data = it)
            }
    }

    fun processIntent(intent: SaleIntent) {
        when (intent) {
            is SaleIntent.SendOrder -> {
                sendOrder(intent.order)
                deleteAll()
                _state.update { current ->
                    current.copy(data = emptyList())
                }
            }

            is SaleIntent.CancelOrder -> {
                deleteAll()
                _state.update { current ->
                    current.copy(data = emptyList())
                }
            }
        }
    }

    private fun sendOrder(order: Order) = viewModelScope.launch {
        sendOrderUseCase(order)
    }

    private fun deleteAll() =
        viewModelScope.launch {
            deleteAllScrapsUseCase()
        }

}