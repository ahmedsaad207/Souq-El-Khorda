package com.delighted2wins.souqelkhorda.features.chat.data.remote

import com.delighted2wins.souqelkhorda.features.chat.domain.entity.Message

interface ChatRemoteDataSource {
    suspend fun sendMessage(message: Message)
    fun getMessages(orderId: String, offerId: String, onMessage: (Message) -> Unit)
}
