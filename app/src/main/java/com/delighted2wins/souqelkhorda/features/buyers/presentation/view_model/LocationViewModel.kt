package com.delighted2wins.souqelkhorda.features.buyers.presentation.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.utils.GPSLocation
import com.delighted2wins.souqelkhorda.features.buyers.presentation.contract.LocationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private val _uiState = MutableStateFlow(LocationUiState())
    val uiState: StateFlow<LocationUiState> = _uiState.asStateFlow()

    private val context get() = getApplication<Application>()

    fun updateGpsState(enabled: Boolean) {
        _uiState.update { it.copy(isGpsEnabled = enabled) }
    }

    fun fetchLocation(onValueChange: (Double, Double) -> Unit ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val location = GPSLocation.getLocation(context)
                location?.let {
                    val locString = "${it.latitude}, ${it.longitude}"
                    _uiState.update { s -> s.copy(locationText = locString) }
                    onValueChange(it.latitude,it.longitude)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
