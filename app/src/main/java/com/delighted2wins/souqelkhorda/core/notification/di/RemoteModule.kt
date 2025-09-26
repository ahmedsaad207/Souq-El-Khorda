package com.delighted2wins.souqelkhorda.core.notification.di

import android.content.Context
import com.delighted2wins.souqelkhorda.core.AppConstant
import com.delighted2wins.souqelkhorda.core.notification.data.remote.service.FcmApiService
import com.delighted2wins.souqelkhorda.core.notification.data.remote.datasource.FcmRemoteDataSource
import com.delighted2wins.souqelkhorda.core.notification.data.remote.datasource.FcmRemoteDataSourceImpl
import com.delighted2wins.souqelkhorda.core.notification.data.repository.FcmRepositoryImpl
import com.delighted2wins.souqelkhorda.core.notification.domain.repository.FcmRepository
import com.delighted2wins.souqelkhorda.core.notification.domain.usecases.SendNotificationUseCase
import com.delighted2wins.souqelkhorda.core.notification.utils.FcmTokenManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
            .baseUrl(AppConstant.FCM_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FcmApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFcmTokenManager(@ApplicationContext context: Context): FcmTokenManager {
        return FcmTokenManager(context)
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