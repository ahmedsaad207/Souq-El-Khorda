package com.delighted2wins.souqelkhorda.features.market.di

import com.delighted2wins.souqelkhorda.features.market.data.remote.MarketRemoteDataSource
import com.delighted2wins.souqelkhorda.features.market.data.remote.MarketRemoteDataSourceImpl
import com.delighted2wins.souqelkhorda.features.market.data.repository.MarketRepositoryImpl
import com.delighted2wins.souqelkhorda.features.market.domain.repository.MarketRepository
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetMarketOrdersUseCase
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserDataByIdUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MarketDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindMarketRemoteDataSource(
        impl: MarketRemoteDataSourceImpl
    ): MarketRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMarketRepository(
        impl: MarketRepositoryImpl
    ): MarketRepository

}

@Module
@InstallIn(ViewModelComponent::class)
object MarketUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetMarketOrdersUseCase(
        repository: MarketRepository
    ): GetMarketOrdersUseCase = GetMarketOrdersUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetUserDataByIdUseCase(
        repository: MarketRepository
    ): GetUserDataByIdUseCase = GetUserDataByIdUseCase(repository)

}
