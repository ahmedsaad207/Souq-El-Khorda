package com.delighted2wins.souqelkhorda.features.offers.di

import com.delighted2wins.souqelkhorda.features.offers.data.remote.OffersRemoteDataSource
import com.delighted2wins.souqelkhorda.features.offers.data.remote.OffersRemoteDataSourceImpl
import com.delighted2wins.souqelkhorda.features.offers.data.repository.OffersRepositoryImpl
import com.delighted2wins.souqelkhorda.features.offers.domain.repository.OffersRepository
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.DeleteOfferUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.DeleteOffersByOrderIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.GetOfferDetailsByIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.GetOffersByBuyerUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.GetOffersByOrderIdUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.MakeOfferUseCase
import com.delighted2wins.souqelkhorda.features.offers.domain.usecase.UpdateOfferStatusUseCase
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
abstract class OffersDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindOffersRemoteDataSource(
        impl: OffersRemoteDataSourceImpl
    ): OffersRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindOffersRepository(
        impl: OffersRepositoryImpl
    ): OffersRepository
}


@Module
@InstallIn(ViewModelComponent::class)
object OffersUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideMakeOfferUseCase(
        repository: OffersRepository
    ): MakeOfferUseCase = MakeOfferUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideUpdateOfferStatusUseCase(
        repository: OffersRepository
    ): UpdateOfferStatusUseCase = UpdateOfferStatusUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideDeleteOfferUseCase(
        repository: OffersRepository
    ): DeleteOfferUseCase = DeleteOfferUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetOfferDetailsByIdUseCase(
        repository: OffersRepository
    ): GetOfferDetailsByIdUseCase = GetOfferDetailsByIdUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetOffersByBuyerIdUseCase(
        repository: OffersRepository
    ): GetOffersByBuyerUseCase = GetOffersByBuyerUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetOffersByOrderIdUseCase(
        repository: OffersRepository
    ): GetOffersByOrderIdUseCase = GetOffersByOrderIdUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideDeleteOffersByOrderIdUseCase(
        repository: OffersRepository
    ): DeleteOffersByOrderIdUseCase = DeleteOffersByOrderIdUseCase(repository)


}