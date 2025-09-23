package com.delighted2wins.souqelkhorda.core.di

import android.content.Context
import androidx.room.Room
import com.delighted2wins.souqelkhorda.features.sell.data.local.db.ScrapDatabase
import com.delighted2wins.souqelkhorda.features.sell.domain.repo.OrderRepository
import com.delighted2wins.souqelkhorda.features.sell.domain.usecase.SendOrderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ScrapDatabase =
        Room.databaseBuilder(
            context,
            ScrapDatabase::class.java,
            "souq_el_khorda.db"
        ).build()

    @Provides
    fun provideScrapDoa(db: ScrapDatabase) = db.scrapDao()
}