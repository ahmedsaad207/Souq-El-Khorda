package com.delighted2wins.souqelkhorda.features.chat.domain.entity

data class Message(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val orderId: String = "",
    val offerId: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
)