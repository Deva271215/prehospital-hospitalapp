package com.g_one_hospitalapp.utilities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.g_one_hospitalapp.MainActivity
import com.g_one_hospitalapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class FCMService: FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        if (p0.notification != null) {
            showNotification(
                    p0.notification?.title.toString(),
                    p0.notification?.body.toString(),
                    p0.notification?.clickAction.toString(),
                    p0.data["chat_id"].toString()
            )
        }
    }

    private fun showNotification(title: String, body: String, clickAction: String, chatRoomId: String) {
        val channelId = "default"
        var intent = Intent(clickAction)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(MainActivity.CHAT_ROOM_ID, chatRoomId)

        val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationId = Random.nextInt()
        val channel = NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            createNotificationChannel(channel)
            notify(notificationId, builder.build())
        }
    }
}