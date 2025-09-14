package com.delighted2wins.souqelkhorda.features.authentication.data.repo

import com.delighted2wins.souqelkhorda.features.authentication.data.model.SignUpRequestDto
import com.delighted2wins.souqelkhorda.features.authentication.data.remote.IAuthenticationRemoteDataSource
import com.delighted2wins.souqelkhorda.features.authentication.domain.repo.IAuthenticationRepo
import javax.inject.Inject

class AuthenticationRepoImp @Inject constructor(
    val remoteDataSource: IAuthenticationRemoteDataSource
): IAuthenticationRepo {
    override suspend fun login(email: String, password: String) =
        remoteDataSource.loginWithEmail(email, password)

    override  suspend fun signUp(
        signUpRequestDto: SignUpRequestDto
    ) = remoteDataSource.signUpWithEmail(
        signUpRequestDto
    )
    override fun logout() = remoteDataSource.logout()
}