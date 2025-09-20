package com.delighted2wins.souqelkhorda.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scraps")
data class Scrap(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String,
    val unit: String,
    val amount: Double,
    val description: String,
    val images: String = "https://media.wired.com/photos/593261cab8eb31692072f129/3:2/w_2560%2Cc_limit/85120553.jpg"
)
