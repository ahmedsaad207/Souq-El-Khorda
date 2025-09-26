package com.delighted2wins.souqelkhorda.features.chat.domain.usecase

import com.delighted2wins.souqelkhorda.features.chat.domain.entity.Message
import com.delighted2wins.souqelkhorda.features.chat.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(message: Message) {
        repository.sendMessage(message = message)
    }
}
