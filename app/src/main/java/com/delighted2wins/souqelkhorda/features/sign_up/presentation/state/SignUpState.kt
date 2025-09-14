package com.delighted2wins.souqelkhorda.features.sign_up.presentation.state

sealed class SignUpState<out T> {
    data object Loading : SignUpState<Nothing>()
    data class Success<out T>(val data: T) : SignUpState<T>()
    data class Error(val message: String) : SignUpState<Nothing>()
}