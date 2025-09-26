package com.delighted2wins.souqelkhorda.app.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.imageLoader
import coil.request.ImageRequest
import com.delighted2wins.souqelkhorda.R
import com.delighted2wins.souqelkhorda.app.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.content.edit
import com.delighted2wins.souqelkhorda.features.authentication.data.local.IAuthenticationLocalDataSource
import com.delighted2wins.souqelkhorda.features.authentication.data.remote.IAuthenticationRemoteDataSource
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PushNotificationService: FirebaseMessagingService() {
    @Inject
    lateinit var localDataSource: IAuthenticationLocalDataSource
    @Inject
    lateinit var remoteDataSource: IAuthenticationRemoteDataSource

    override fun onNewToken(token: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            localDataSource.saveFcmToken(token)
        } else {
            remoteDataSource.updateFcmToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data
        val title = data["title"] ?: message.notification?.title ?: ""
        val body = data["message"] ?: message.notification?.body ?: ""
        val imageUrl = data["imageUrl"]
        val action = data["action"]
        val extras = data

        createNotificationChannelIfNeeded()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            extras.forEach { (k, v) -> putExtra(k, v) }
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            generateNotificationId(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        CoroutineScope(Dispatchers.IO).launch {
            val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            if (!imageUrl.isNullOrBlank()) {
                try {
                    val request = ImageRequest.Builder(applicationContext)
                        .data(imageUrl)
                        .allowHardware(false)
                        .build()
                    val bmp = request.context.imageLoader.execute(request).drawable?.toBitmap()

                    val style = NotificationCompat.BigPictureStyle()
                        .bigPicture(bmp)
                        .bigLargeIcon(null as Bitmap?)
                    builder.setStyle(style)
                } catch (e: Exception) {
                }
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(applicationContext)
                    .notify(generateNotificationId(), builder.build())
            }
        }
    }

    private fun saveTokenToFirestore(uid: String, token: String) {
        val db = FirebaseFirestore.getInstance()
        val data = mapOf("fcmToken" to token)

        db.collection("users").document(uid)
            .set(data, SetOptions.merge())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    localDataSource.clearFcmToken()
                }
            }
    }

    private fun createNotificationChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Default Channel"
            val descriptionText = "General notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun generateNotificationId(): Int =
        (System.currentTimeMillis() % Int.MAX_VALUE).toInt()

    companion object {
        private const val CHANNEL_ID = "default_channel_v1"
    }
}