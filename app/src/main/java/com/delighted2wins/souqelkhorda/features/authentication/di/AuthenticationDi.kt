package com.delighted2wins.souqelkhorda.features.authentication.di

import com.delighted2wins.souqelkhorda.features.authentication.data.local.AuthenticationLocalDataSourceImp
import com.delighted2wins.souqelkhorda.features.authentication.data.local.IAuthenticationLocalDataSource
import com.delighted2wins.souqelkhorda.features.authentication.data.remote.AuthenticationRemoteDataSourceImp
import com.delighted2wins.souqelkhorda.features.authentication.data.remote.IAuthenticationRemoteDataSource
import com.delighted2wins.souqelkhorda.features.authentication.data.repo.AuthenticationRepoImp
import com.delighted2wins.souqelkhorda.features.authentication.domain.repo.IAuthenticationRepo
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.CashUserCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.FreeUserCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.GetCashUserCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.LoginUseCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.LogoutUseCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.ObserveUserUseCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.SignUpUseCase
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

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(
        impl: AuthenticationLocalDataSourceImp
    ): IAuthenticationLocalDataSource

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
    @Provides
    @ViewModelScoped
    fun provideCashUserDataUseCase(repo: IAuthenticationRepo) : CashUserCase = CashUserCase(
        repo
    )

    @Provides
    @ViewModelScoped
    fun provideGetCashedUserDataUseCase(repo: IAuthenticationRepo) : GetCashUserCase =GetCashUserCase(
        repo
    )

    @Provides
    @ViewModelScoped
    fun provideFreeUserDataUseCase(repo: IAuthenticationRepo) : FreeUserCase =FreeUserCase(
        repo
    )

    @Provides
    @ViewModelScoped
    fun provideObserveUserUseCase(repo: IAuthenticationRepo): ObserveUserUseCase = ObserveUserUseCase(repo)
}