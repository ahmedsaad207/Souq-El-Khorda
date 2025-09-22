package com.delighted2wins.souqelkhorda.features.history.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.delighted2wins.souqelkhorda.features.history.domain.usecase.GetUserOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getUserOrdersUseCase: GetUserOrdersUseCase
): ViewModel() {


}