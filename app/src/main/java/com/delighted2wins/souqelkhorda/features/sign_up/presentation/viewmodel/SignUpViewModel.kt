package com.delighted2wins.souqelkhorda.features.sign_up.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.core.extensions.isAddress
import com.delighted2wins.souqelkhorda.core.extensions.isEmail
import com.delighted2wins.souqelkhorda.core.extensions.isPassword
import com.delighted2wins.souqelkhorda.core.extensions.isPhoneNumber
import com.delighted2wins.souqelkhorda.core.extensions.isUserName
import com.delighted2wins.souqelkhorda.features.sign_up.data.model.SignUpRequestDto
import com.delighted2wins.souqelkhorda.features.sign_up.presentation.state.SignUpState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel() : ViewModel() {
    private val _registerState = MutableStateFlow<SignUpState<Boolean>>(SignUpState.Loading)
    val registerState = _registerState.asStateFlow()
    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    fun signUp(signUpRequestDto: SignUpRequestDto) {

        viewModelScope.launch(Dispatchers.IO) {
            if (!signUpRequestDto.name.isUserName()) {
                emitError("Enter valid name")
                return@launch
            }
            if (!signUpRequestDto.email.isEmail()) {
                emitError("Enter valid email")
                return@launch
            }
            if (!signUpRequestDto.password.isPassword()) {
                emitError("Password must be at least 6 characters ...")
                return@launch
            }
            if (signUpRequestDto.passwordConfirmation != signUpRequestDto.password) {
                emitError("Password confirmation not match")
                return@launch
            }
            if (!signUpRequestDto.phone.isPhoneNumber()) {
                emitError("Enter valid phone number")
                return@launch
            }
            if (!signUpRequestDto.address.isAddress()) {
                emitError("Enter valid address, must be at least 15 characters")
                return@launch
            }


        }

    }


    private suspend fun emitError(msg: String) {
        _message.emit(msg)
        _registerState.emit(SignUpState.Error(msg))
    }

}

