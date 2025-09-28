package com.delighted2wins.souqelkhorda.features.authentication.domain.repo

import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser
import com.delighted2wins.souqelkhorda.features.authentication.data.model.SignUpRequestDto
import com.delighted2wins.souqelkhorda.features.authentication.presentation.state.AuthenticationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IAuthenticationRepo {
    val userFlow: StateFlow<AuthUser?>
    suspend fun login(email: String, password: String) :Flow<AuthenticationState>
    suspend fun signUp(
        signUpRequestDto: SignUpRequestDto
    ):Flow<AuthenticationState>
    fun logout()
    fun cashUserData( user: AuthUser)
    fun getCashedUserData() : AuthUser
    fun freeUserCash()
}