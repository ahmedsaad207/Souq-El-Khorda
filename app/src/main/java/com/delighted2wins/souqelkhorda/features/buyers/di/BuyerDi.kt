package com.delighted2wins.souqelkhorda.features.buyers.di

import com.delighted2wins.souqelkhorda.features.buyers.data.remote.BuyerRemoteDataSourceImp
import com.delighted2wins.souqelkhorda.features.buyers.data.remote.IBuyerRemoteDataSource
import com.delighted2wins.souqelkhorda.features.buyers.data.repo.BuyerRepoImp
import com.delighted2wins.souqelkhorda.features.buyers.domain.repo.IBuyerRepo
import com.delighted2wins.souqelkhorda.features.buyers.domain.use_case.GetBuyersCase
import com.delighted2wins.souqelkhorda.features.buyers.domain.use_case.IsBuyersCase
import com.delighted2wins.souqelkhorda.features.buyers.domain.use_case.RegisterBuyersCase
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
abstract class BuyerDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindBuyerRemoteDataSource(
        impl: BuyerRemoteDataSourceImp
    ): IBuyerRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindBuyerRepository(
        impl: BuyerRepoImp
    ): IBuyerRepo
}



@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule{
    @Provides
    @ViewModelScoped
    fun provideRegisterBuyer(repo: IBuyerRepo): RegisterBuyersCase = RegisterBuyersCase(
        repo
    )

    @Provides
    @ViewModelScoped
    fun provideGetBuyers(repo: IBuyerRepo): GetBuyersCase = GetBuyersCase(
        repo
    )

    @Provides
    @ViewModelScoped
    fun provideIsBuyer(repo: IBuyerRepo): IsBuyersCase = IsBuyersCase(
        repo
    )


}
