package com.delighted2wins.souqelkhorda.features.additem.di

import com.delighted2wins.souqelkhorda.features.sell.data.local.ScrapesLocalDataSource
import com.delighted2wins.souqelkhorda.features.sell.data.local.ScrapesLocalDataSourceImpl
import com.delighted2wins.souqelkhorda.features.additem.data.repo.ScrapesRepoImpl
import com.delighted2wins.souqelkhorda.features.additem.domain.repo.ScrapesRepo
import com.delighted2wins.souqelkhorda.features.additem.domain.usecase.SaveScrapUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AddItemModule {

    @Singleton
    @Binds
    abstract fun bindScrapesRepo(impl: ScrapesRepoImpl): ScrapesRepo

    @Binds
    abstract fun bindScrapsLocalDataSource(impl: ScrapesLocalDataSourceImpl): ScrapesLocalDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object AddItemUseCaseModule {

    @Provides
    fun provideSaveScrapUseCase(repo: ScrapesRepo) = SaveScrapUseCase(repo)
}