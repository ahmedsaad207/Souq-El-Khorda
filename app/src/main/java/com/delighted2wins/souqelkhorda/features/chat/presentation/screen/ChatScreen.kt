package com.delighted2wins.souqelkhorda.features.chat.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.app.theme.Til
import com.delighted2wins.souqelkhorda.core.components.CachedUserImage
import com.delighted2wins.souqelkhorda.features.chat.presentation.component.ShimmerMessagePlaceholder
import com.delighted2wins.souqelkhorda.features.chat.presentation.contract.ChatIntent
import com.delighted2wins.souqelkhorda.features.chat.presentation.viewmodel.ChatViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    orderId: String,
    sellerId: String,
    buyerId: String,
    offerId: String,
    onBack: () -> Unit
) {
    val state by viewModel.state
    val listState = rememberLazyListState()
    val systemUiController = rememberSystemUiController()
    val useDarkIcons: Boolean = !isSystemInDarkTheme()
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    LaunchedEffect(orderId, buyerId, sellerId, offerId) {
        viewModel.init(
            orderId = orderId,
            buyerId = buyerId,
            sellerId = sellerId,
            offerId = offerId
        )
    }
    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.lastIndex)
        }
    }

    SideEffect {
        systemUiController.isSystemBarsVisible = true
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (state.otherUser != null) {
                            CachedUserImage(
                                imageUrl = state.otherUser!!.imageUrl,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(state.otherUser!!.name, style = MaterialTheme.typography.titleLarge)
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .background(Color.LightGray, RoundedCornerShape(27.dp))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .height(20.dp)
                                    .width(120.dp)
                                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            if (isRtl) Icons.Default.ArrowForward else Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets.systemBars
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f).fillMaxWidth().padding(8.dp),
                reverseLayout = true
            ) {
                if (state.isLoading) {
                    items(20) { index ->
                        ShimmerMessagePlaceholder(isMe = index % 2 == 0)
                    }
                } else {
                    items(state.messages) { msg ->
                        val isMe = msg.senderId == state.currentUser?.id
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = if (isMe) Alignment.CenterEnd else Alignment.CenterStart
                        ) {
                            Surface(
                                color = if (isMe) MaterialTheme.colorScheme.secondary else Color.LightGray,
                                shape = RoundedCornerShape(12.dp),
                                tonalElevation = 2.dp,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(
                                    text = msg.message,
                                    color = if (isMe) Color.White else Color.Black,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = state.messageText,
                    onValueChange = { viewModel.onIntent(ChatIntent.SetMessageText(it)) },
                    placeholder = { Text("Type a message...") },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .border(width = 1.dp, color = Til, shape = RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
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
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                    )
                )
                IconButton(
                    onClick = {
                        if (state.messageText.isNotBlank()) {
                            viewModel.onIntent(ChatIntent.SendMessage(state.messageText))
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(62.dp)
                            .clip(CircleShape)
                            .background(
                                if (state.messageText.isNotBlank())
                                    MaterialTheme.colorScheme.secondary
                                else
                                    Color.LightGray
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector =  Icons.Default.Send,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .graphicsLayer {
                                    scaleX = if (isRtl) -1f else 1f
                                }
                        )
                    }
                }
            }
        }
    }
}
