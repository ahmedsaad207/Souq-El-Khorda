package com.delighted2wins.souqelkhorda.core.di

import com.delighted2wins.souqelkhorda.features.sell.data.remote.cloudinary.CloudinaryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CloudinaryModule {

    @Provides
    @Singleton
    fun provideCloudinaryService(): CloudinaryService = CloudinaryService()
}
