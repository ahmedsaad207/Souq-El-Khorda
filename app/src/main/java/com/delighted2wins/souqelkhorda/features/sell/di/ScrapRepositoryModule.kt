package com.delighted2wins.souqelkhorda.features.sell.di

import com.delighted2wins.souqelkhorda.features.sell.data.repo.ScrapRepositoryImpl
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.ScrapRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScrapRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindScrapsRepo(impl: ScrapRepositoryImpl): ScrapRepository
}

