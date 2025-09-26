package com.delighted2wins.souqelkhorda.app.services

import android.content.Intent
import com.delighted2wins.souqelkhorda.app.MainActivity
import com.delighted2wins.souqelkhorda.core.notification.utils.FcmTokenManager
import com.delighted2wins.souqelkhorda.core.notification.utils.NotificationUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PushNotificationService : FirebaseMessagingService() {
    @Inject
    lateinit var fcmTokenManager: FcmTokenManager

    override fun onNewToken(token: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            fcmTokenManager.cacheToken(token)
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                fcmTokenManager.syncTokenToFirestore(uid)
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data
        val title = data["title"] ?: message.notification?.title ?: ""
        val body = data["message"] ?: message.notification?.body ?: ""
        val imageUrl = data["imageUrl"]
        val action = data["action"]

        NotificationUtils.createChannel(applicationContext)

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            data.forEach { (k, v) -> putExtra(k, v) }
        }

        NotificationUtils.showNotification(
            context = applicationContext,
            title = title,
            body = body,
            intent = intent,
            imageUrl = imageUrl
        )
    }
}