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
    private lateinit var preference: UserPreference

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        preference = UserPreference(applicationContext)
        preference.setChatRoomId(p0.data["chat_id"].toString())

        showNotification(
                p0.data["title"].toString(),
                p0.data["body"].toString(),
                p0.data["click_action"].toString()
        )
    }

    private fun showNotification(title: String, body: String, clickAction: String) {
        val channelId = "default"
        var intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        val builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_notification, "Ke Home Screen", pendingIntent)

        val notificationId = Random.nextInt()
        val channel = NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            createNotificationChannel(channel)
            notify(notificationId, builder.build())
        }
    }
}