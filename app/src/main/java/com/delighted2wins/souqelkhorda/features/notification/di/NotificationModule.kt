package com.delighted2wins.souqelkhorda.features.notification.di

import com.delighted2wins.souqelkhorda.features.notification.data.remote.NotificationRemoteDataSource
import com.delighted2wins.souqelkhorda.features.notification.data.remote.NotificationRemoteDataSourceImpl
import com.delighted2wins.souqelkhorda.features.notification.data.repository.NotificationsRepositoryImpl
import com.delighted2wins.souqelkhorda.features.notification.domain.repository.NotificationsRepository
import com.delighted2wins.souqelkhorda.features.notification.domain.usecases.GetNotificationsUseCase
import com.delighted2wins.souqelkhorda.features.notification.domain.usecases.MarkNotificationAsReadUseCase
import com.delighted2wins.souqelkhorda.features.notification.domain.usecases.ObserveNotificationsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {
    @Binds
    @Singleton
    abstract fun bindNotificationRemoteDataSource(
        impl: NotificationRemoteDataSourceImpl
    ): NotificationRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        impl: NotificationsRepositoryImpl
    ): NotificationsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ProfileUseCaseModule {
    @Provides
    @Singleton
    fun provideGetNotificationUseCase(
        repository: NotificationsRepository
    ): GetNotificationsUseCase = GetNotificationsUseCase(repository)

    @Provides
    @Singleton
    fun provideMarkNotificationAsReadUseCase(
        repository: NotificationsRepository
    ): MarkNotificationAsReadUseCase = MarkNotificationAsReadUseCase(repository)

    @Provides
    @Singleton
    fun provideObserveNotificationsUseCase(
        repository: NotificationsRepository
    ): ObserveNotificationsUseCase = ObserveNotificationsUseCase(repository)
}