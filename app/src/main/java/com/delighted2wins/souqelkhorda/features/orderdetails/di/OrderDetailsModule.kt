package com.delighted2wins.souqelkhorda.features.orderdetails.data


import com.delighted2wins.souqelkhorda.features.orderdetails.data.remote.OrderDetailsRemoteDataSource
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.repository.OrderDetailsRepository
import com.delighted2wins.souqelkhorda.features.orderdetails.domain.usecase.GetOrderDetailsUseCase
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
abstract class OrderDetailsDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindOrderDetailsRemoteDataSource(
        impl: OrderDetailsRemoteDataSourceImpl
    ): OrderDetailsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindOrderDetailsRepository(
        impl: OrderDetailsRepositoryImpl
    ): OrderDetailsRepository
}

@Module
@InstallIn(ViewModelComponent::class)
object OrderDetailsUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetOrderDetailsUseCase(
        repository: OrderDetailsRepository
    ): GetOrderDetailsUseCase = GetOrderDetailsUseCase(repository)
}
