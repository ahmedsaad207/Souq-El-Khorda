package com.delighted2wins.souqelkhorda.features.history.di

import com.delighted2wins.souqelkhorda.features.history.data.repository.HistoryRepositoryImpl
import com.delighted2wins.souqelkhorda.features.history.data.remote.HistoryRemoteDataSource
import com.delighted2wins.souqelkhorda.features.history.data.remote.HistoryRemoteDataSourceImpl
import com.delighted2wins.souqelkhorda.features.history.domain.repository.HistoryRepository
import com.delighted2wins.souqelkhorda.features.history.domain.usecase.GetUserOrdersUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class HistoryModule {
    @Binds
    @Singleton
    abstract fun bindHistoryRemoteDataSource(
        impl: HistoryRemoteDataSourceImpl
    ): HistoryRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindHistoryRepository(
        impl: HistoryRepositoryImpl
    ): HistoryRepository
}

@Module
@InstallIn(SingletonComponent::class)
object HistoryUseCaseModule {
    @Provides
    @Singleton
    fun provideGetUserOrdersUseCase(
        repository: HistoryRepository
    ): GetUserOrdersUseCase = GetUserOrdersUseCase(repository)

}
