package com.delighted2wins.souqelkhorda.core.model

data class MainUserDto(
    val id : String,
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val passwordConfirmation: String,
    val governorate: String,
    val address: String,
    val userImage: String? = null
)