package com.delighted2wins.souqelkhorda.features.authentication.di

import com.delighted2wins.souqelkhorda.features.authentication.data.remote.AuthenticationRemoteDataSourceImp
import com.delighted2wins.souqelkhorda.features.authentication.data.remote.IAuthenticationRemoteDataSource
import com.delighted2wins.souqelkhorda.features.authentication.data.repo.AuthenticationRepoImp
import com.delighted2wins.souqelkhorda.features.authentication.domain.repo.IAuthenticationRepo
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.LoginUseCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.LogoutUseCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.SignUpUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
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
abstract class AuthenticationDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        impl: AuthenticationRemoteDataSourceImp
    ): IAuthenticationRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthenticationRepository(
        impl: AuthenticationRepoImp
    ): IAuthenticationRepo


}



@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

}

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule{
    @Provides
    @ViewModelScoped
    fun provideLoginUseCase(repo: IAuthenticationRepo): LoginUseCase = LoginUseCase(
        repo
    )

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(repo: IAuthenticationRepo): SignUpUseCase = SignUpUseCase(
        repo
    )

    @Provides
    @ViewModelScoped
    fun provideLogoutUseCase(repo: IAuthenticationRepo): LogoutUseCase = LogoutUseCase(
        repo
    )
}