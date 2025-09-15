package com.delighted2wins.souqelkhorda.features.authentication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthMsg
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.LoginUseCase
import com.delighted2wins.souqelkhorda.features.authentication.presentation.state.AuthenticationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow<AuthenticationState>(AuthenticationState.Idle)
    val loginState = _loginState.asStateFlow()
    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (email.isEmpty() || password.isEmpty()) {
                _message.emit(AuthMsg.EMPTYFILDES.getMsg())
                return@launch
            }
            loginUseCase(email, password)
                .catch { e ->
                    _loginState.emit(AuthenticationState.Error(AuthMsg.UNAUTHORIZED.getMsg()))
                }
                .collect { state ->
                    _loginState.emit(state)
                    when (state) {
                        is AuthenticationState.Success -> {
                            val user = state.userAuth
                            _message.emit(AuthMsg.LOGINSUCCESS.getMsg() + " ${user.name}")
                        }
                        is AuthenticationState.Error -> {
                            _message.emit(AuthMsg.UNAUTHORIZED.getMsg())
                        }
                        else -> Unit
                    }
                }
        }
    }
}