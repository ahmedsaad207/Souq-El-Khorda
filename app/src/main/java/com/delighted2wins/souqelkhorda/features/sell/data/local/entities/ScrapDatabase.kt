package com.delighted2wins.souqelkhorda.features.sell.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scraps")
data class ScrapDatabase(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val unit: String,
    val amount: Double,
    val description: String,
    val images: String
)
