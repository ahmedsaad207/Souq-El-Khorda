package com.delighted2wins.souqelkhorda.features.market.data

data class ScrapItem(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val weight: Int,
    val status: ScrapStatus,
    val date: String,
    val userId: Int
)