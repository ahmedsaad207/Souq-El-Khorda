package com.delighted2wins.souqelkhorda.features.profile.data.local

import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import com.delighted2wins.souqelkhorda.core.AppConstant.LANGUAGE
import javax.inject.Inject

class ProfileLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: ISharedPreferences,
) : ProfileLocalDataSource {
    override fun saveData(key: String, value: Any) {
        sharedPreferences.saveData(key, value)
    }
    override fun <T> fetchData(key: String, defaultValue: T): T {
        return sharedPreferences.fetchData(key, defaultValue)
    }

    override fun saveLanguage(code: String) {
        sharedPreferences.saveData(LANGUAGE, code)
    }

    override fun getLanguage(): String {
        return sharedPreferences.fetchData(LANGUAGE, "")
    }
}