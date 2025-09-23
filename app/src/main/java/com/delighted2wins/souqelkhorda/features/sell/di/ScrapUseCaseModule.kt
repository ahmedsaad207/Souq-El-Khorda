package com.delighted2wins.souqelkhorda.features.sell.di

import com.delighted2wins.souqelkhorda.features.sell.domain.repo.OrderRepository
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.ScrapRepository
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.DeleteAllScrapsUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.DeleteOrderUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.DeleteScrapByIdUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.GetScrapesUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.SaveScrapUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.SendOrderUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.UploadScrapImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ScrapUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetScrapsUseCase(
        repo: ScrapRepository
    ): GetScrapesUseCase = GetScrapesUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideDeleteAllScrapsUseCase(repo: ScrapRepository) = DeleteAllScrapsUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideSaveScrapUseCase(repo: ScrapRepository) = SaveScrapUseCase(repo)

    @Provides
    fun provideSendOrderUseCase(repo: OrderRepository) = SendOrderUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideDeleteOrderUseCase(repo: OrderRepository) = DeleteOrderUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideDeleteScrapByIdUseCase(repo: ScrapRepository) = DeleteScrapByIdUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideUploadScrapImagesUseCase(repo: OrderRepository) = UploadScrapImagesUseCase(repo)
}