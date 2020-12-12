package com.example.android_firebase_study

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "onMessageReceived: ${remoteMessage.from}")

        notification()
    }

    private fun notification() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            return
        }

        // BigTextStyle(Text中心の通知)
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText("Big text")
            .setBigContentTitle("Big title")
            .setSummaryText("hogehoge!")

        val notifyIntent = Intent(this, MainActivity::class.java)
        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent =
            PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationCompatBuilder = NotificationCompat.Builder(
            this,
            CHANNEL_ID,
        )

        val notification = notificationCompatBuilder
            .setStyle(bigTextStyle)
            .setContentTitle("title ")
            .setContentText("Content Text")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_alarm_white_48dp
                )
            )
            .setContentIntent(pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))

            // Category
            .setCategory(Notification.CATEGORY_REMINDER)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)

        notificationManagerCompat.notify(
            (0..999).random(),
            notification
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken　From: $token")


        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // Android O(8.0) 以上で通知を使用する場合は通知チャンネルを作成する必要があります
        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            val channelName = "Sample Reminder"
            val channelDescription = "Description"
            // 重要度
            val channelImportance = NotificationManager.IMPORTANCE_DEFAULT
            val channelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC

            val notificationChannel =
                NotificationChannel(CHANNEL_ID, channelName, channelImportance)
            notificationChannel.description = channelDescription
            notificationChannel.enableVibration(false)
            notificationChannel.lockscreenVisibility = channelLockscreenVisibility

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        private const val TAG = "MyFirebaseMessaging"
        private const val CHANNEL_ID = "CHANNEL_ID"
    }
}