package com.delighted2wins.souqelkhorda.features.chat.di

import com.delighted2wins.souqelkhorda.features.chat.data.remote.ChatRemoteDataSource
import com.delighted2wins.souqelkhorda.features.chat.data.remote.ChatRemoteDataSourceImpl
import com.delighted2wins.souqelkhorda.features.chat.data.repository.ChatRepositoryImpl
import com.delighted2wins.souqelkhorda.features.chat.domain.repository.ChatRepository
import com.delighted2wins.souqelkhorda.features.chat.domain.usecase.GetMessagesUseCase
import com.delighted2wins.souqelkhorda.features.chat.domain.usecase.SendMessageUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindChatRemoteDataSource(
        impl: ChatRemoteDataSourceImpl
    ): ChatRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        impl: ChatRepositoryImpl
    ): ChatRepository

}

@Module
@InstallIn(ViewModelComponent::class)
object ChatUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetMessagesUseCase(
        repository: ChatRepository
    ):  GetMessagesUseCase = GetMessagesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideSendMessageUseCase(
        repository: ChatRepository
    ): SendMessageUseCase = SendMessageUseCase(repository)

}