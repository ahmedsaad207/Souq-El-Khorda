package com.delighted2wins.souqelkhorda.features.authentication.data.model

import com.delighted2wins.souqelkhorda.core.model.MainUserDto

data class AuthUser(
    val id : Int =0,
    val name: String ="",
    val email: String= "",
    val phone: String= "",
    val governorate: String="",
    val address: String="",
    val imageUrl: String? = ""
)

fun MainUserDto.toAuthUser() = AuthUser(
    id = id,
    name = name,
    email = email,
    phone = phone,
    governorate = governorate,
    address = address,
    imageUrl = userImage
)