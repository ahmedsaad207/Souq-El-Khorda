package com.delighted2wins.souqelkhorda.features.sell.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.model.Order
import com.delighted2wins.souqelkhorda.core.model.Scrap
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.DeleteAllScrapsUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.DeleteScrapByIdUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.GetScrapesUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.SaveScrapUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.SendOrderUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.UploadScrapImagesUseCase
import com.delighted2wins.souqelkhorda.features.sell.presentation.contract.SellIntent
import com.delighted2wins.souqelkhorda.features.sell.presentation.contract.SellState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellViewModel @Inject constructor(
    private val getScrapsUseCase: GetScrapesUseCase,
    private val deleteAllScrapsUseCase: DeleteAllScrapsUseCase,
    private val sendOrderUseCase: SendOrderUseCase,
    private val saveScrapUseCase: SaveScrapUseCase,
    private val deleteScrapByIdUseCase: DeleteScrapByIdUseCase,
    private val uploadScrapImagesUseCase: UploadScrapImagesUseCase,
) : ViewModel() {

    private var _state = MutableStateFlow(SellState())
    val state = _state.asStateFlow()

    init {
        loadScraps()
    }

    private fun loadScraps() = viewModelScope.launch(Dispatchers.IO) {
        _state.value = _state.value.copy(isLoading = true)
        getScrapsUseCase().catch {
                _state.value = _state.value.copy(isLoading = false, err = it.message)
            }.collect {
                _state.value = _state.value.copy(isLoading = false, data = it)
            }
    }

    fun processIntent(intent: SellIntent) {
        when (intent) {
            is SellIntent.SendOrder -> submitOrder(intent.order)

            is SellIntent.AddScrap -> saveScrap(intent.scrap)

            is SellIntent.DeleteScrap -> deleteScrapById(intent.scrap.id)
        }
    }

    private fun deleteScrapById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteScrapByIdUseCase(id).catch {
                _state.emit(_state.value.copy(err = it.message))
            }.collect {
                if (it != 0) {
                    _state.emit(_state.value.copy(isScrapDeleted = true))
                    loadScraps()
                } else {
                    _state.emit(_state.value.copy(err = "Scrap not found"))
                }
            }
    }

    private fun saveScrap(scrap: Scrap) = viewModelScope.launch {
        try {
            _state.value = _state.value.copy(isLoading = true, isScrapSaved = false)
            saveScrapUseCase(scrap)
            _state.value = _state.value.copy(isLoading = false, isScrapSaved = true)
            loadScraps()
        } catch (e: Exception) {
            _state.value = _state.value.copy(err = e.message)
        }
    }

    private fun submitOrder(order: Order) = viewModelScope.launch {
        _state.value = _state.value.copy(isOrderSubmitted = false)
        try {
            val updatedScraps = uploadScrapImagesUseCase(order.scraps)
            val updatedOrder = order.copy(scraps = updatedScraps)
            sendOrderUseCase(updatedOrder)
            delay(500)
            clearOrder()
        } catch (e: Exception) {
            _state.value =
                _state.value.copy(isLoading = false, err = e.message, isOrderSubmitted = false)
        }
    }

    private fun clearOrder() = viewModelScope.launch {
        deleteAllScrapsUseCase()
        _state.value = _state.value.copy(data = emptyList(), isOrderSubmitted = true)
    }

    fun resetScrapSavedFlag() {
        _state.value = _state.value.copy(isScrapSaved = false)
    }

    fun resetScrapDeletedFlag() {
        _state.value = _state.value.copy(isScrapDeleted = false)
    }

    fun resetOrderSubmittedFlag() {
        _state.value = _state.value.copy(isOrderSubmitted = false)
    }

}