package com.delighted2wins.souqelkhorda.features.profile.di

import com.delighted2wins.souqelkhorda.features.profile.data.remote.ProfileRemoteDataSource
import com.delighted2wins.souqelkhorda.features.profile.data.remote.ProfileRemoteDataSourceImpl
import com.delighted2wins.souqelkhorda.features.profile.data.repository.ProfileRepositoryImpl
import com.delighted2wins.souqelkhorda.features.profile.domain.repository.ProfileRepository
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.GetUserProfileUseCase
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.UpdateUserEmailUseCase
import com.delighted2wins.souqelkhorda.features.profile.domain.usecase.UpdateUserProfileUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {
    @Binds
    @Singleton
    abstract fun bindProfileRemoteDataSource(
        impl: ProfileRemoteDataSourceImpl
    ): ProfileRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ProfileUseCaseModule {
    @Provides
    @Singleton
    fun provideUpdateUserProfileUseCase(
        repository: ProfileRepository
    ): UpdateUserProfileUseCase = UpdateUserProfileUseCase(repository)

    @Provides
    @Singleton
    fun provideGetUserProfileUseCase(
        repository: ProfileRepository
    ): GetUserProfileUseCase = GetUserProfileUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateUserNameUseCase(
        repository: ProfileRepository
    ): UpdateUserEmailUseCase = UpdateUserEmailUseCase(repository)
}