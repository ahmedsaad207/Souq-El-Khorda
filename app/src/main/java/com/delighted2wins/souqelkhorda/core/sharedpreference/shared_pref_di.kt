package com.delighted2wins.souqelkhorda.core.sharedpreference


import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class shared_pref_di {

    @Binds
    @Singleton
    abstract fun bindISharedPreferences(
        sharedPreferencesImpl: SharedPreferencesImpl
    ): ISharedPreferences

}