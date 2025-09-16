package com.delighted2wins.souqelkhorda.features.sale.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scraps")
data class Scrap(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String,
    val unit: String,
    val amount: Int,
    val description: String,
//    val images: List<String> = emptyList()
)
