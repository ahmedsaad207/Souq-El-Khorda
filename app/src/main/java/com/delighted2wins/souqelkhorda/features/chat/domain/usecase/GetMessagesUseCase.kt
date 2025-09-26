package com.delighted2wins.souqelkhorda.features.chat.domain.usecase

import com.delighted2wins.souqelkhorda.features.chat.domain.entity.Message
import com.delighted2wins.souqelkhorda.features.chat.domain.repository.ChatRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke(orderId: String,offerId: String, onMessage: (Message) -> Unit) {
        repository.getMessages(orderId, offerId, onMessage)
    }
}