package com.delighted2wins.souqelkhorda.features.authentication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.extensions.isAddress
import com.delighted2wins.souqelkhorda.core.extensions.isEmail
import com.delighted2wins.souqelkhorda.core.extensions.isPassword
import com.delighted2wins.souqelkhorda.core.extensions.isPhoneNumber
import com.delighted2wins.souqelkhorda.core.extensions.isUserName
import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthMsg
import com.delighted2wins.souqelkhorda.features.authentication.data.model.SignUpRequestDto
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.LogoutUseCase
import com.delighted2wins.souqelkhorda.features.authentication.domain.useCase.SignUpUseCase
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
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val logOutUseCase: LogoutUseCase
) : ViewModel() {
    private val _registerState = MutableStateFlow<AuthenticationState>(AuthenticationState.Idle)
    val registerState = _registerState.asStateFlow()
    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()
    fun signUp(signUpRequestDto: SignUpRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!signUpRequestDto.name.isUserName()) {
                emitError(AuthMsg.USERNAMEVALIDATE.getMsg())
                return@launch
            }
            if (!signUpRequestDto.email.isEmail()) {
                emitError(AuthMsg.EMAILVALIDATE.getMsg())
                return@launch
            }
            if (!signUpRequestDto.password.isPassword()) {
                emitError(AuthMsg.PASSWORDVALIDATE.getMsg())
                return@launch
            }
            if (signUpRequestDto.passwordConfirmation != signUpRequestDto.password) {
                emitError(AuthMsg.PASSWORDCONFIRMATION.getMsg())
                return@launch
            }
            if (!signUpRequestDto.phone.isPhoneNumber()) {
                emitError(AuthMsg.PHONEVALIDATE.getMsg())
                return@launch
            }
            if (signUpRequestDto.governorate.isEmpty()) {
                emitError(AuthMsg.GOVERNORATEVALIDATE.getMsg())
                return@launch
            }
            if (!signUpRequestDto.address.isAddress()) {
                emitError(AuthMsg.ADDRESSVALIDATE.getMsg())
                return@launch
            }
            try {
                signUpUseCase(signUpRequestDto)
                    .catch { emitError(AuthMsg.SIGNUPFAIL.getMsg()) }
                    .collect { state ->
                        _registerState.emit(state)
                        when (state) {
                            is AuthenticationState.Success -> {
//                                logOutUseCase()
                                val user = state.userAuth
                                _message.emit(AuthMsg.SIGNUPSUCCESS.getMsg() )
                            }
                            is AuthenticationState.Error -> {
                                _message.emit(AuthMsg.SIGNUPFAIL.getMsg())
                            }
                            else -> Unit
                        }
                    }
            } catch (e: Exception) {
                emitError(AuthMsg.SIGNUPFAIL.getMsg())
            }
        }
    }
    private suspend fun emitError(msg: String) {
        _message.emit(msg)
        _registerState.emit(AuthenticationState.Error(msg))
    }

}