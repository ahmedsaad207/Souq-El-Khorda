package com.delighted2wins.souqelkhorda.features.myorders.di

import com.delighted2wins.souqelkhorda.features.myorders.data.remote.MyOrdersRemoteDataSource
import com.delighted2wins.souqelkhorda.features.myorders.data.remote.MyOrdersRemoteDataSourceImpl
import com.delighted2wins.souqelkhorda.features.myorders.data.repository.MyOrdersRepositoryImpl
import com.delighted2wins.souqelkhorda.features.myorders.domain.repository.MyOrdersRepository
import com.delighted2wins.souqelkhorda.features.myorders.domain.usecase.LoadCompanyOrdersUseCase
import com.delighted2wins.souqelkhorda.features.myorders.domain.usecase.LoadOffersUseCase
import com.delighted2wins.souqelkhorda.features.myorders.domain.usecase.LoadSellsUseCase
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
abstract class MyOrdersDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindMyOrdersRemoteDataSource(
        impl: MyOrdersRemoteDataSourceImpl
    ): MyOrdersRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMyOrdersRepository(
        impl: MyOrdersRepositoryImpl
    ): MyOrdersRepository

}

@Module
@InstallIn(ViewModelComponent::class)
object MyOrdersUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideLoadSaleOrdersUseCase(
        repository: MyOrdersRepository
    ): LoadCompanyOrdersUseCase = LoadCompanyOrdersUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideLoadSellsUseCase(
        repository: MyOrdersRepository
    ): LoadSellsUseCase = LoadSellsUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideLoadOffersUseCase(
        repository: MyOrdersRepository
    ): LoadOffersUseCase = LoadOffersUseCase(repository)

}

