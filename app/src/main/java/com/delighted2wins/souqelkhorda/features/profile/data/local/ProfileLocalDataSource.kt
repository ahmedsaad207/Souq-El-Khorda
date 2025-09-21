package com.delighted2wins.souqelkhorda.features.profile.data.local

import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser

interface ProfileLocalDataSource {
    fun saveData(key: String, value: Any)
    fun <T> fetchData(key: String, defaultValue: T): T
    fun saveLanguage(code: String)
    fun getLanguage(): String
}