package com.example.simple

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
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
        val notificationChannelId = createNotificationChannel()
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText("Big text")
            .setBigContentTitle("Big title")
            .setSummaryText("hogehoge!")

        val notifyIntent = Intent(this, MainActivity::class.java)
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        val pendingIntent =
            PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        NotificationCompat.Builder(
            this,

            )
    }


    fun createNotificationChannel(): String {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {

            val channelId = "channel_reminder_1"
            val channelName = "Sample Reminder"
            val channelDescription = "Description"
            val channelImportance = NotificationManager.IMPORTANCE_DEFAULT
            val channelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC

            val notificationChannel = NotificationChannel(channelId, channelName, channelImportance)
            notificationChannel.description = channelDescription
            notificationChannel.enableVibration(false)
            notificationChannel.lockscreenVisibility = channelLockscreenVisibility

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            return channelId
        } else {
            return ""
        }
    }
}