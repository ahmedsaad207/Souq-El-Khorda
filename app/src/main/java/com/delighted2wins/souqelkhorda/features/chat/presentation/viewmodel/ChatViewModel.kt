package com.delighted2wins.souqelkhorda.features.chat.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delighted2wins.souqelkhorda.features.chat.domain.entity.Message
import com.delighted2wins.souqelkhorda.features.chat.domain.usecase.GetMessagesUseCase
import com.delighted2wins.souqelkhorda.features.chat.domain.usecase.SendMessageUseCase
import com.delighted2wins.souqelkhorda.features.chat.presentation.contract.ChatIntent
import com.delighted2wins.souqelkhorda.features.chat.presentation.contract.ChatState
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetCurrentUserUseCase
import com.delighted2wins.souqelkhorda.features.market.domain.usecase.GetUserDataByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserDataByIdUseCase: GetUserDataByIdUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ChatState())
    val state = _state

    private var currentUserId: String? = null
    private var otherUserId: String? = null
    private var orderId: String? = null
    private var offerId: String? = null
    private var initialized = false


    fun init(orderId: String, buyerId: String, sellerId: String, offerId: String) {
        if (initialized) {
            Log.d("ChatViewModel", "init skipped (already initialized)")
            return
        }
        initialized = true

        this.orderId = orderId
        this.offerId = offerId

        Log.d("ChatViewModel", "init: $orderId")
        Log.d("ChatViewModel", "init: $offerId")
        Log.d("ChatViewModel", "init: $buyerId")
        Log.d("ChatViewModel", "init: $sellerId")

        viewModelScope.launch {
            val currentUser = getCurrentUserUseCase()
            currentUserId = currentUser.id

            otherUserId = if (currentUserId == buyerId) sellerId else buyerId
            val otherUser = getUserDataByIdUseCase(otherUserId!!)

            _state.value = _state.value.copy(
                currentUser = currentUser,
                otherUser = otherUser,
            )
        }
        loadMessages(orderId!!, offerId!!)
    }

    fun onIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.SetMessageText ->
                _state.value = _state.value.copy(messageText = intent.text)

            is ChatIntent.SendMessage -> {
                sendMessage(intent.message)
                _state.value = _state.value.copy(messageText = "")
            }

            is ChatIntent.LoadMessages ->
                loadMessages(intent.orderId, intent.offerId)
        }
    }

    private fun sendMessage(text: String) {
        val senderId = currentUserId ?: return
        val receiverId = otherUserId ?: return
        val order = orderId ?: return
        val offer = offerId ?: return

        if (text.isBlank()) return

        val message = Message(
            senderId = senderId,
            receiverId = receiverId,
            orderId = order,
            offerId = offer,
            message = text,
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            sendMessageUseCase(message)
            _state.value = _state.value.copy(messageText = "")
        }
    }

    private fun loadMessages(orderId: String, offerId: String) {
        viewModelScope.launch {
            try {
                getMessagesUseCase(orderId, offerId) { message ->
                    val currentMessages = _state.value.messages
                    _state.value = _state.value.copy(isLoading = false)

                    if (currentMessages.none { it.timestamp == message.timestamp && it.senderId == message.senderId }) {
                        _state.value = _state.value.copy(
                            messages = listOf(message) + currentMessages
                        )
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }

}
