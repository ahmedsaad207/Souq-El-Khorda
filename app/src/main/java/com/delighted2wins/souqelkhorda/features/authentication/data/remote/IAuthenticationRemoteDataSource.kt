package com.delighted2wins.souqelkhorda.features.authentication.data.remote

import com.delighted2wins.souqelkhorda.features.authentication.data.model.SignUpRequestDto
import com.delighted2wins.souqelkhorda.features.authentication.presentation.state.AuthenticationState
import kotlinx.coroutines.flow.Flow

interface IAuthenticationRemoteDataSource {
    suspend fun signUpWithEmail(signUpRequestDto: SignUpRequestDto): Flow<AuthenticationState>
    suspend fun loginWithEmail(email: String, password: String): Flow<AuthenticationState>
    fun logout()
}