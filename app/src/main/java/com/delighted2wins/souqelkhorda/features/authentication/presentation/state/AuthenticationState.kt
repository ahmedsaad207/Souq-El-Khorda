package com.delighted2wins.souqelkhorda.features.authentication.presentation.state

import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser

sealed class AuthenticationState {
    data object Loading : AuthenticationState()
    data class Success(val userAuth: AuthUser) : AuthenticationState()
    data class Error(val errorMsg: String) : AuthenticationState()
    data object Idle : AuthenticationState()
}

