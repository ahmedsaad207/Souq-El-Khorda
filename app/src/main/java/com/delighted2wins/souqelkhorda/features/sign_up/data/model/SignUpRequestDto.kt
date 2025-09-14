package com.delighted2wins.souqelkhorda.features.sign_up.data.model

data class SignUpRequestDto(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val passwordConfirmation: String,
    val governorate: String,
    val address: String
)


