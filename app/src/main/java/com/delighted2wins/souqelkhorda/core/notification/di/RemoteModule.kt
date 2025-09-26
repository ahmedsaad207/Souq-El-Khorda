package com.delighted2wins.souqelkhorda.core.notification.di

import com.delighted2wins.souqelkhorda.core.notification.data.FcmApiService
import com.delighted2wins.souqelkhorda.core.notification.data.FcmRemoteDataSource
import com.delighted2wins.souqelkhorda.core.notification.data.FcmRemoteDataSourceImpl
import com.delighted2wins.souqelkhorda.core.notification.data.FcmRepositoryImpl
import com.delighted2wins.souqelkhorda.core.notification.domain.FcmRepository
import com.delighted2wins.souqelkhorda.core.notification.domain.SendNotificationUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    fun provideFcmApiService(): FcmApiService {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FcmApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideSendNotificationUseCase(fcmRepository: FcmRepository): SendNotificationUseCase {
        return SendNotificationUseCase(fcmRepository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindFcmRemoteDataSource(
        fcmRemoteDataSourceImpl: FcmRemoteDataSourceImpl
    ): FcmRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindFcmRepository(
        fcmRepositoryImpl: FcmRepositoryImpl
    ): FcmRepository
}