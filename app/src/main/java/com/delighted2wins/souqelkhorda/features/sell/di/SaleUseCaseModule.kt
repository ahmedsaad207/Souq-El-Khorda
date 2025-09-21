package com.delighted2wins.souqelkhorda.features.sell.di

import com.delighted2wins.souqelkhorda.features.additem.domain.repo.ScrapesRepo
import com.delighted2wins.souqelkhorda.features.additem.domain.usecase.DeleteAllScrapsUseCase
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.GetScrapesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SaleUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetScrapesUseCase(
        repo: ScrapesRepo
    ): GetScrapesUseCase = GetScrapesUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideDeleteAllScrapsUseCase(repo: ScrapesRepo) = DeleteAllScrapsUseCase(repo)
}