package com.delighted2wins.souqelkhorda.features.authentication.data.model

data class SignUpRequestDto(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val passwordConfirmation: String,
    val governorate: String,
    val address: String,
    val area: String
)