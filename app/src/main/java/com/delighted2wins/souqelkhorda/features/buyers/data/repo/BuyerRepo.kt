package com.delighted2wins.souqelkhorda.features.buyers.data.repo

import com.delighted2wins.souqelkhorda.features.buyers.data.model.BuyerDto
import com.delighted2wins.souqelkhorda.features.buyers.data.remote.IBuyerRemoteDataSource
import com.delighted2wins.souqelkhorda.features.buyers.domain.repo.IBuyerRepo
import javax.inject.Inject

class BuyerRepoImp @Inject constructor(
    private val remoteDataSource: IBuyerRemoteDataSource
): IBuyerRepo {
    override suspend fun registerBuyer(buyerModel: BuyerDto) = remoteDataSource.registerBuyer(buyerModel)

    override suspend fun getNearstBuyers() = remoteDataSource.getNearstBuyers()

    override suspend fun isBuyer(userID: String) = remoteDataSource.isBuyer(userID)

}