package com.delighted2wins.souqelkhorda.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Offer(
    val userId: String,
    val price: Int,
    val date: Long
): Parcelable
