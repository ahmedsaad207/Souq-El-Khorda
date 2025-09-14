package com.delighted2wins.souqelkhorda.features.authentication.domain.repo

import com.delighted2wins.souqelkhorda.features.authentication.data.model.SignUpRequestDto
import com.delighted2wins.souqelkhorda.features.authentication.presentation.state.AuthenticationState
import kotlinx.coroutines.flow.Flow

interface IAuthenticationRepo {
    suspend fun login(email: String, password: String) :Flow<AuthenticationState>

    suspend fun signUp(
        signUpRequestDto: SignUpRequestDto
    ):Flow<AuthenticationState>
    fun logout()
}