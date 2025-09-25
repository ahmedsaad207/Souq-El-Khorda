package com.delighted2wins.souqelkhorda.core.model

import com.delighted2wins.souqelkhorda.core.enums.OfferStatus

data class Offer(
    val offerId: String = "",
    val orderId: String = "",
    val buyerId: String = "",
    val offerPrice: Int = 0,
    val status: OfferStatus = OfferStatus.PENDING,
    val date: Long = 0L
)
