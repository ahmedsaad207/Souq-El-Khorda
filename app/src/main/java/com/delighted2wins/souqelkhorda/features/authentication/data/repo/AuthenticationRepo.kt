package com.delighted2wins.souqelkhorda.features.authentication.data.repo

import com.delighted2wins.souqelkhorda.features.authentication.data.local.IAuthenticationLocalDataSource
import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser
import com.delighted2wins.souqelkhorda.features.authentication.data.model.SignUpRequestDto
import com.delighted2wins.souqelkhorda.features.authentication.data.remote.IAuthenticationRemoteDataSource
import com.delighted2wins.souqelkhorda.features.authentication.domain.repo.IAuthenticationRepo
import com.delighted2wins.souqelkhorda.features.authentication.presentation.contract.AuthenticationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthenticationRepoImp @Inject constructor(
    val remoteDataSource: IAuthenticationRemoteDataSource,
    val localDataSource: IAuthenticationLocalDataSource
): IAuthenticationRepo {

    private val _userFlow = MutableStateFlow<AuthUser?>(null)
    override val userFlow = _userFlow.asStateFlow()
    init {
        val cachedUser = localDataSource.getCashedUser()
        if (cachedUser.email.isNotEmpty()) {
            _userFlow.value = cachedUser
        }
    }
    override suspend fun login(email: String, password: String): Flow<AuthenticationState> {
        return remoteDataSource.loginWithEmail(email, password).onEach { state ->
            if (state is AuthenticationState.Success) {
                localDataSource.cashUserData(state.userAuth)
                _userFlow.value = state.userAuth
            }
        }
    }

    override  suspend fun signUp(
        signUpRequestDto: SignUpRequestDto
    ) : Flow<AuthenticationState> =
        remoteDataSource.signUpWithEmail(signUpRequestDto).onEach { state ->
            if (state is AuthenticationState.Success) {
                localDataSource.cashUserData(state.userAuth)
                _userFlow.value = state.userAuth
            }
        }

    override fun logout() {
        remoteDataSource.logout()
        localDataSource.freeUserData()
        _userFlow.value = null

    }

    override fun cashUserData( user: AuthUser){
        localDataSource.cashUserData(user)
        _userFlow.value = user
    }
    override fun getCashedUserData() = localDataSource.getCashedUser()
    override fun freeUserCash() {
        localDataSource.freeUserData()
        _userFlow.value = null

    }
}