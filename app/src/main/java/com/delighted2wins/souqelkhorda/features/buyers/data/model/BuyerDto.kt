package com.delighted2wins.souqelkhorda.features.buyers.data.model

data class BuyerDto(
    val buyerID: String,
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val governorate: String = "",
    val address: String = "",
    val area: String = "",
    val imageUrl: String? = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val types: List<String> = emptyList()
)

