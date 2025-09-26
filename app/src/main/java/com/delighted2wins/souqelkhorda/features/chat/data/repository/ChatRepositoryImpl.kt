package com.delighted2wins.souqelkhorda.features.chat.data.repository

import com.delighted2wins.souqelkhorda.features.chat.data.remote.ChatRemoteDataSource
import com.delighted2wins.souqelkhorda.features.chat.domain.entity.Message
import com.delighted2wins.souqelkhorda.features.chat.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val remoteDataSource: ChatRemoteDataSource
): ChatRepository {

    override suspend fun sendMessage(message: Message) {
        remoteDataSource.sendMessage(message = message)
    }

    override fun getMessages(orderId: String,offerId: String, onMessage: (Message) -> Unit) {
        remoteDataSource.getMessages(orderId,offerId, onMessage)
    }
}