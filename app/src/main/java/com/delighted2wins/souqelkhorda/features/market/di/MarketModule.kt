package com.delighted2wins.souqelkhorda.features.market.di

import com.delighted2wins.souqelkhorda.features.market.data.remote.MarketRemoteDataSource
import com.delighted2wins.souqelkhorda.features.market.data.repository.MarketRepositoryImpl
import com.delighted2wins.souqelkhorda.features.market.domain.repository.MarketRepository
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetScrapOrdersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MarketModule {

    @Provides
    @ViewModelScoped
    fun provideMarketRepository(
        remoteDataSource: MarketRemoteDataSource
    ): MarketRepository = MarketRepositoryImpl(remoteDataSource)

    @Provides
    @ViewModelScoped
    fun provideGetScrapOrdersUseCase(
        repository: MarketRepository
    ): GetScrapOrdersUseCase = GetScrapOrdersUseCase(repository)

}
