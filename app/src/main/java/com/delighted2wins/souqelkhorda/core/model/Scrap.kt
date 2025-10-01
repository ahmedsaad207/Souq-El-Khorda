package com.delighted2wins.souqelkhorda.core.model


data class Scrap(
    val id: Int = 0,
    val category: String = "",
    val unit: String = "",
    val amount: String = "0",
    val description: String = "",
    val images: List<String> = emptyList()
)
