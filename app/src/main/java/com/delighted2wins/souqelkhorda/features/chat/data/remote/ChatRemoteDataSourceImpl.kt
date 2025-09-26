package com.delighted2wins.souqelkhorda.features.chat.data.remote

import com.delighted2wins.souqelkhorda.features.chat.domain.entity.Message
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

class ChatRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ChatRemoteDataSource {

    override suspend fun sendMessage(message: Message) {
        firestore.collection("chats")
            .document(message.orderId)
            .collection(message.offerId)
            .add(message)
    }

    override fun getMessages(orderId: String,offerId: String, onMessage: (Message) -> Unit) {
        firestore.collection("chats")
            .document(orderId)
            .collection(offerId)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, error ->
                if (error != null || snapshots == null) return@addSnapshotListener
                for (docChange in snapshots.documentChanges) {
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        docChange.document.toObject(Message::class.java).let { onMessage(it) }
                    }
                }
            }
    }
}