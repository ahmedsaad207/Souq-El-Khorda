package com.delighted2wins.souqelkhorda.features.chat

data class ChatMessage(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val orderId: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)