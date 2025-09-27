package com.delighted2wins.souqelkhorda.features.authentication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.AuthMsgEnum
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.CashUserCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.GetCashUserCase
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
    val loginUseCase: LoginUseCase,
    val cashUserCase: CashUserCase,
    val getCashUserCase: GetCashUserCase,
) : ViewModel() {
    private val _loginState = MutableStateFlow<AuthenticationState>(AuthenticationState.Idle)
    val loginState = _loginState.asStateFlow()
    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (email.isEmpty() || password.isEmpty()) {
                _message.emit(AuthMsgEnum.EMPTYFILDES.getMsg())
                return@launch
            }
            loginUseCase(email, password)
                .catch { e ->
                    _loginState.emit(AuthenticationState.Error(AuthMsgEnum.UNAUTHORIZED.getMsg()))
                }
                .collect { state ->
                    _loginState.emit(state)
                    when (state) {
                        is AuthenticationState.Success -> {
                            val user = state.userAuth
                            cashUserCase(user)
                            _message.emit(AuthMsgEnum.LOGINSUCCESS.getMsg())
                        }

                        is AuthenticationState.Error -> {
                            _message.emit(AuthMsgEnum.SIGNUPFAIL.getMsg())
                        }

                        else -> Unit
                    }
                }
        }
    }

   suspend fun isLoggedIn() : Boolean{
        return getCashUserCase().email != ""
    }
}