package com.delighted2wins.souqelkhorda.core.di

import com.delighted2wins.souqelkhorda.features.sell.data.remote.cloudinary.CloudinaryService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
