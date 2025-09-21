package com.delighted2wins.souqelkhorda.core.di

import com.delighted2wins.souqelkhorda.features.sell.data.remote.OrdersRemoteDataSource
import com.delighted2wins.souqelkhorda.features.sell.data.remote.OrdersRemoteDataSourceImpl
import com.delighted2wins.souqelkhorda.features.sell.data.repo.OrdersRepositoryImpl
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OrdersModule {


    @Binds
    @Singleton
    abstract fun bindOrdersRepo(impl: OrdersRepositoryImpl): OrdersRepository

    @Binds
    abstract fun bindOrdersRemoteDataSource(impl: OrdersRemoteDataSourceImpl): OrdersRemoteDataSource
}