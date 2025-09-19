package com.delighted2wins.souqelkhorda.features.market.domain.entities

data class User(
    val id: Int,
    val name: String,
    val location: String,
    val imageUrl: String? = null
)