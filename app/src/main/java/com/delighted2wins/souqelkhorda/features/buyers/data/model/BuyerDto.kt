package com.delighted2wins.souqelkhorda.features.buyers.data.model

import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser

data class BuyerDto(
    val buyerID: String="",
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

fun AuthUser.toBuyerDto(latitude: Double, longitude: Double, scrapTypes: List<String>): BuyerDto {
    return BuyerDto(
        buyerID = id,
        name = name,
        email = email,
        phone = phone,
        governorate = governorate,
        address = address,
        area = area,
        imageUrl = imageUrl,
        longitude = latitude,
        latitude = longitude,
        types = scrapTypes
    )
}


