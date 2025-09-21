package com.delighted2wins.souqelkhorda.core.model

import android.os.Parcelable
import com.delighted2wins.souqelkhorda.core.enums.OrderStatus
import com.delighted2wins.souqelkhorda.core.enums.OrderType
import com.delighted2wins.souqelkhorda.core.enums.UserRole
import kotlinx.parcelize.Parcelize

data class Order(
    val orderId: String = "",
    val userId: String = "",
    val scraps: List<Scrap> = emptyList(),
    val type: OrderType = OrderType.SALE,
    val status: OrderStatus = OrderStatus.PENDING,
    val date: Long = System.currentTimeMillis(),
    val offers: List<Offer> = emptyList(),
    val userRole: UserRole = UserRole.SELLER,
    val title: String = "",
    val description: String = "",
    val price: Int = 0
)
