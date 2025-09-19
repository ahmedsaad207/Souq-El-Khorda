package com.delighted2wins.souqelkhorda.features.authentication.data.local

import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser
import javax.inject.Inject

class AuthenticationLocalDataSourceImp @Inject constructor(
    private val sharedPreferences: ISharedPreferences,
) : IAuthenticationLocalDataSource {
    override fun saveData(key: String, value: Any) {
        sharedPreferences.saveData(key, value)
    }
    override fun <T> fetchData(key: String, defaultValue: T): T {
        return sharedPreferences.fetchData(key, defaultValue)
    }

    override fun cashUserData( user: AuthUser){
        saveData("user_id",user.id)
        saveData("user_name",user.name)
        saveData("user_email",user.email)
        saveData("user_phone",user.phone)
        saveData("user_governorate",user.governorate)
        saveData("user_address",user.address)
        saveData("user_image",user.imageUrl?:"")
    }

    override fun getCashedUser(): AuthUser {
        return AuthUser(
            id = fetchData("user_id", ""),
            name = fetchData("user_name", ""),
            email = fetchData("user_email", ""),
            phone = fetchData("user_phone", ""),
            governorate = fetchData("user_governorate", ""),
            address = fetchData("user_address", ""),
            imageUrl = fetchData("user_image", "")
        )
    }

    override fun freeUserData(){
        saveData("user_id", "")
        saveData("user_name", "")
        saveData("user_email", "")
        saveData("user_phone", "")
        saveData("user_governorate", "")
        saveData("user_address", "")
        saveData("user_image", "")
    }
}