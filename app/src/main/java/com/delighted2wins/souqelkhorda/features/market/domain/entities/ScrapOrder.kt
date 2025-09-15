package com.delighted2wins.souqelkhorda.features.market.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ScrapOrder(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val price: Double,
    val date: String,
    val userId: Int,
    val items: List<ScrapOrderItem> = emptyList()
) : Parcelable

