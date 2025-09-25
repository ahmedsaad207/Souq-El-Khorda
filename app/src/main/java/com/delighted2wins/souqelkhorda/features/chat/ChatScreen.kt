package com.delighted2wins.souqelkhorda.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.delighted2wins.souqelkhorda.app.theme.Til

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    sellerId: String,
    buyerId: String,
    orderId: String,
    onBack: () -> Unit,
) {
    var input by remember { mutableStateOf("") }
    val title = "Buyer Name"
    val messages = listOf("Hi, I accepted your offer.", "Great! When can I pick it up?")


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                reverseLayout = true
            ) {
                items(messages) { msg ->
                    val isMe = true //msg.senderId == sellerId
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = if (isMe) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        Surface(
                            color = if (isMe) MaterialTheme.colorScheme.primary else Color.LightGray,
                            shape = RoundedCornerShape(12.dp),
                            tonalElevation = 2.dp,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = "", // msg.text,
                                color = if (isMe) Color.White else Color.Black,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    placeholder = { Text("Type a message...") },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .border(width = 2.dp, color = Til, shape = RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Til,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                IconButton(
                    onClick = {
                        if (input.isNotBlank()) {
                          //  onSendMessage(input.trim())  // Handle sending the message
                            input = ""
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Til
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewChatScreen() {
    val dummyMessages = listOf(
        ChatMessage(
            id = "1",
            senderId = "seller123",
            receiverId = "buyer456",
            orderId = "order1",
            text = "Hi, I accepted your offer."
        ),
        ChatMessage(
            id = "2",
            senderId = "buyer456",
            receiverId = "seller123",
            orderId = "order1",
            text = "Great! When can I pick it up?"
        )
    )

    ChatScreen(
        sellerId = "seller123",
        buyerId = "buyer456",
        orderId = "order1",
        onBack = {},
    )
}
