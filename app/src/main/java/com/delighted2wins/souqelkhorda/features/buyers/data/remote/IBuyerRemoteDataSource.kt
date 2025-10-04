package com.delighted2wins.souqelkhorda.features.buyers.data.remote

import com.delighted2wins.souqelkhorda.features.buyers.data.model.BuyerDto
import com.delighted2wins.souqelkhorda.features.buyers.presentation.contract.BuyerState
import kotlinx.coroutines.flow.Flow

interface IBuyerRemoteDataSource {
    suspend fun registerBuyer(buyerModel: BuyerDto): Flow<BuyerState>
    suspend fun getNearstBuyers(): Flow<BuyerState>
    suspend fun isBuyer(userID: String): Boolean
}