package com.delighted2wins.souqelkhorda.core.di

import com.delighted2wins.souqelkhorda.core.FcmApiService
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
            .baseUrl("http://192.168.1.101:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FcmApiService::class.java)
    }

}