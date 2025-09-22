package com.delighted2wins.souqelkhorda.features.myorders.domain.usecase

import com.delighted2wins.souqelkhorda.features.myorders.domain.repository.MyOrdersRepository
import javax.inject.Inject

class LoadSaleOrdersUseCase @Inject constructor(
    private val repository: MyOrdersRepository
){
    suspend operator fun invoke() = repository.getSaleOrders()
}