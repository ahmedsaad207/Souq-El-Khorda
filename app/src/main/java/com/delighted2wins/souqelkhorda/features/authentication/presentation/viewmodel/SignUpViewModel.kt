package com.delighted2wins.souqelkhorda.features.authentication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.enums.AuthMsgEnum
import com.delighted2wins.souqelkhorda.core.extensions.isAddress
import com.delighted2wins.souqelkhorda.core.extensions.isEmail
import com.delighted2wins.souqelkhorda.core.extensions.isPassword
import com.delighted2wins.souqelkhorda.core.extensions.isPhoneNumber
import com.delighted2wins.souqelkhorda.core.extensions.isUserName
import com.delighted2wins.souqelkhorda.features.authentication.data.model.SignUpRequestDto
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
) : ViewModel() {
    private val _registerState = MutableStateFlow<AuthenticationState>(AuthenticationState.Idle)
    val registerState = _registerState.asStateFlow()
    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()
    fun signUp(signUpRequestDto: SignUpRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!signUpRequestDto.name.isUserName()) {
                emitError(AuthMsgEnum.USERNAMEVALIDATE.getMsg())
                return@launch
            }
            if (!signUpRequestDto.email.isEmail()) {
                emitError(AuthMsgEnum.EMAILVALIDATE.getMsg())
                return@launch
            }
            if (!signUpRequestDto.password.isPassword()) {
                emitError(AuthMsgEnum.PASSWORDVALIDATE.getMsg())
                return@launch
            }
            if (signUpRequestDto.passwordConfirmation != signUpRequestDto.password) {
                emitError(AuthMsgEnum.PASSWORDCONFIRMATION.getMsg())
                return@launch
            }
            if (!signUpRequestDto.phone.isPhoneNumber()) {
                emitError(AuthMsgEnum.PHONEVALIDATE.getMsg())
                return@launch
            }
            if (signUpRequestDto.governorate.isEmpty()) {
                emitError(AuthMsgEnum.GOVERNORATEVALIDATE.getMsg())
                return@launch
            }
            if (signUpRequestDto.area.isEmpty() || signUpRequestDto.area.length < 3) {
                emitError(AuthMsgEnum.AREA.getMsg())
                return@launch
            }
            if (!signUpRequestDto.address.isAddress()) {
                emitError(AuthMsgEnum.ADDRESSVALIDATE.getMsg())
                return@launch
            }
            try {
                signUpUseCase(signUpRequestDto)
                    .catch { emitError(AuthMsgEnum.SIGNUPFAIL.getMsg()) }
                    .collect { state ->
                        _registerState.emit(state)
                        when (state) {
                            is AuthenticationState.Success -> {
                                _message.emit(AuthMsgEnum.SIGNUPSUCCESS.getMsg())
                            }

                            is AuthenticationState.Error -> {
                                _message.emit(AuthMsgEnum.SIGNUPFAIL.getMsg())
                            }

                            else -> Unit
                        }
                    }
            } catch (e: Exception) {
                emitError(AuthMsgEnum.SIGNUPFAIL.getMsg())
            }
        }
    }

    private suspend fun emitError(msg: String) {
        _message.emit(msg)
        _registerState.emit(AuthenticationState.Error(msg))
    }

}