package com.delighted2wins.souqelkhorda.features.sale.domain.entities

data class Order(
    val userId: String,
    val scraps: List<Scrap>
)
