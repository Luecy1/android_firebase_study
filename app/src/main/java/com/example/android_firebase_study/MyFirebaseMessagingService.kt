package com.example.android_firebase_study

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "From: $token")

    }

    companion object {
        private const val TAG = "MyFirebaseMessaging"
    }
}