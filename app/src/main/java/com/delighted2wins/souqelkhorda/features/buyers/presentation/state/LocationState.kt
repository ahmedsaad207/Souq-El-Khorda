package com.delighted2wins.souqelkhorda.features.buyers.presentation.state

data class LocationUiState(
    val locationText: String = "—",
    val isLoading: Boolean = false,
    val isGpsEnabled: Boolean = false
)
