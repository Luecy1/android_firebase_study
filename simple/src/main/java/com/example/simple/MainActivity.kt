package com.example.simple

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.simple.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            notification()

        }
    }

    private fun notification() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            return
        }

        // NotificationChannelを作成する
        // NotificationChannelごとに表示と音声を設定
        val notificationChannelId = createNotificationChannel()

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
            notificationChannelId,
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


    private fun createNotificationChannel(): String {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            return ""
        }

        val channelId = "channel_reminder_1"
        val channelName = "Sample Reminder"
        val channelDescription = "Description"
        // 重要度
        val channelImportance = NotificationManager.IMPORTANCE_DEFAULT
        val channelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC

        val notificationChannel = NotificationChannel(channelId, channelName, channelImportance)
        notificationChannel.description = channelDescription
        notificationChannel.enableVibration(false)
        notificationChannel.lockscreenVisibility = channelLockscreenVisibility

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

        return channelId
    }
}