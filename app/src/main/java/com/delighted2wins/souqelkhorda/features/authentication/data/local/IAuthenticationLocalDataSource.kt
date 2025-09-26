package com.delighted2wins.souqelkhorda.features.authentication.data.local

import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser

interface IAuthenticationLocalDataSource {
    fun saveData(key: String, value: Any)
    fun <T> fetchData(key: String, defaultValue: T): T
    fun freeUserData()
    fun getCashedUser(): AuthUser
    fun cashUserData( user: AuthUser)

    fun saveFcmToken(token: String)
    fun getFcmToken(): String?
    fun clearFcmToken()
}