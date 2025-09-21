package com.delighted2wins.souqelkhorda.features.market.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarketUser(
    val id: Int,
    val name: String,
    val location: String,
    val imageUrl: String? = null
): Parcelable