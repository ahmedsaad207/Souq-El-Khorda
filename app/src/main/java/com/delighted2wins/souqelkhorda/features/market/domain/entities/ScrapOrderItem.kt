package com.delighted2wins.souqelkhorda.features.market.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScrapOrderItem(
    val id: Int,
    val name: String,
    val weight: Int,
    val quantity: Int? = null,
    val images: List<String>? = null
): Parcelable
