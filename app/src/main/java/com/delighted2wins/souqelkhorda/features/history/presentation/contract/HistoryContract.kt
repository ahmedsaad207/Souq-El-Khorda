package com.delighted2wins.souqelkhorda.features.history.presentation.contract

import com.delighted2wins.souqelkhorda.features.history.domain.entity.HistoryOrder

interface HistoryContract {

    data class State(
        val userId: String = "",
        val isLoading: Boolean = false,
        val orders: List<HistoryOrder> = emptyList(),
        val completedCount: Int = 0,
        val pendingCount: Int = 0,
        val cancelledCount: Int = 0,
        val selectedTabIndex: Int = 0,
        val error: String? = null,
    )

    sealed interface Intent {
        data object LoadOrders : Intent
        data class FilterOrders(val tab: Int) : Intent
    }

}