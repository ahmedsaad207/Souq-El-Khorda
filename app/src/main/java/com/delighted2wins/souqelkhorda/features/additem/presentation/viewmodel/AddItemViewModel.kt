package com.delighted2wins.souqelkhorda.features.additem.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.features.additem.domain.usecase.SaveScrapUseCase
import com.delighted2wins.souqelkhorda.features.additem.presentation.AddItemIntent
import com.delighted2wins.souqelkhorda.features.additem.presentation.AddItemState
import com.delighted2wins.souqelkhorda.core.model.Scrap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val saveScrapUseCase: SaveScrapUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(AddItemState())
    val state = _state.asStateFlow()

    fun processIntent(intent: AddItemIntent) {
        when(intent) {
            is AddItemIntent.AddIntent -> {
                saveScrap(intent.scrap)
            }
            is AddItemIntent.CancelIntent -> {}
        }
    }

    private fun saveScrap(scrap: Scrap) = viewModelScope.launch {
        try {
            _state.value = _state.value.copy(isLoading = true)
            saveScrapUseCase(scrap)
            _state.value = _state.value.copy(isSaved = true)
        } catch (e: Exception) {
            _state.value = _state.value.copy(error = e.message)
        }
    }
}