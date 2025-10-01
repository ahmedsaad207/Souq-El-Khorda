package com.delighted2wins.souqelkhorda.features.chat.domain.repository

import com.delighted2wins.souqelkhorda.features.chat.domain.entity.Message

interface ChatRepository {
    suspend fun sendMessage(message: Message)
    fun getMessages(orderId: String,offerId: String, onMessage: (Message) -> Unit)
    suspend fun deleteChat(orderId: String, offerId: String): Boolean
    suspend fun deleteChatsByOrderId(orderId: String, offerIds: List<String>): Boolean
}