package com.delighted2wins.souqelkhorda.features.market.domain.entities

import com.delighted2wins.souqelkhorda.features.market.domain.entities.ScrapStatus

data class ScrapItem(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val price: Double,
    val weight: Int,
    val quantity: Int? = null,
    val status: ScrapStatus,
    val date: String,
    val images: List<String>? = null,
    val userId: Int,
)