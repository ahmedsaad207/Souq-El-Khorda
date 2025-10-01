package com.delighted2wins.souqelkhorda.features.sell.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scraps")
data class ScrapEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val unit: String,
    val amount: String,
    val description: String,
    val images: List<String>
)