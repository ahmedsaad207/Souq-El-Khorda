package com.delighted2wins.souqelkhorda.features.market.domain.entities

data class MarketUser(
    val id: Int,
    val name: String,
    val location: String,
    val imageUrl: String? = null
)