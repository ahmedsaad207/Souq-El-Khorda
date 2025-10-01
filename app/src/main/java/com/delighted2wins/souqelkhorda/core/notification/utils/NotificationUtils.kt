package com.delighted2wins.souqelkhorda.core.notification.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ImageRequest
import com.delighted2wins.souqelkhorda.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object NotificationUtils {
    private const val CHANNEL_ID = "default_channel_v1"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Default Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "General notifications" }

            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
    }

    fun showNotification(
        context: Context,
        title: String,
        body: String,
        intent: Intent,
        imageUrl: String? = null
    ) {
        val pendingIntent = PendingIntent.getActivity(
            context,
            generateNotificationId(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        CoroutineScope(Dispatchers.IO).launch {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            if (!imageUrl.isNullOrBlank()) {
                try {
                    val request = ImageRequest.Builder(context)
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
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(context)
                    .notify(generateNotificationId(), builder.build())
            }
        }
    }

    private fun generateNotificationId(): Int =
        (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
}
