package com.delighted2wins.souqelkhorda.features.sell.di

import com.delighted2wins.souqelkhorda.features.sell.data.datasource.ScrapLocalDataSource
import com.delighted2wins.souqelkhorda.features.sell.data.datasource.ScrapLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ScrapDataSourceModule {

    @Binds
    abstract fun bindScrapsLocalDataSource(impl: ScrapLocalDataSourceImpl): ScrapLocalDataSource
}