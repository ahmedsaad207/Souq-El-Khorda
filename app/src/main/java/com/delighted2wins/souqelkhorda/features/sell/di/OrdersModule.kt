package com.delighted2wins.souqelkhorda.features.sell.di

import com.delighted2wins.souqelkhorda.features.sell.data.datasource.OrdersRemoteDataSource
import com.delighted2wins.souqelkhorda.features.sell.data.datasource.OrdersRemoteDataSourceImpl
import com.delighted2wins.souqelkhorda.features.sell.data.repo.OrderRepositoryImpl
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.OrderRepository
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
    abstract fun bindOrdersRepo(impl: OrderRepositoryImpl): OrderRepository

    @Binds
    abstract fun bindOrdersRemoteDataSource(impl: OrdersRemoteDataSourceImpl): OrdersRemoteDataSource
}