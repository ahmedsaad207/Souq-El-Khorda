package com.delighted2wins.souqelkhorda.core.di

import android.content.Context
import androidx.room.Room
import com.delighted2wins.souqelkhorda.core.local.db.ScrapeDatabase
import com.delighted2wins.souqelkhorda.features.sale.domain.repo.OrdersRepository
import com.delighted2wins.souqelkhorda.features.sale.domain.usecase.SendOrderUseCase
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
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
    fun provideDatabase(@ApplicationContext context: Context): ScrapeDatabase =
        Room.databaseBuilder(
            context,
            ScrapeDatabase::class.java,
            "souq_el_khorda.db"
        ).build()

    @Provides
    fun provideScrapDoa(db: ScrapeDatabase) = db.scrapDao()

//    @Provides
//    @Singleton
//    fun provideFirestore() = Firebase.firestore

    @Provides
    fun provideSendOrderUseCase(repo: OrdersRepository) = SendOrderUseCase(repo)
}