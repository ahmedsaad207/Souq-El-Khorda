package com.delighted2wins.souqelkhorda.features.authentication.presentation.contract

import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser
import com.delighted2wins.souqelkhorda.features.authentication.data.model.SignUpRequestDto


sealed class AuthenticationIntent {
    data class SignUp(val signUpRequestDto: SignUpRequestDto) : AuthenticationIntent()
    data class Login(val email: String, val password: String) : AuthenticationIntent()
    data object IsLogedOut: AuthenticationIntent()
}

sealed class AuthenticationState {
    data object LoggedIn : AuthenticationState()
    data object LoggedOut : AuthenticationState()
    data object Loading : AuthenticationState()
    data class Success(val userAuth: AuthUser) : AuthenticationState()
    data class Error(val errorMsg: String) : AuthenticationState()
    data object Idle : AuthenticationState()
}

