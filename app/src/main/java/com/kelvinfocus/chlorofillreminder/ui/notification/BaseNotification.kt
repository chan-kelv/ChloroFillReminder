package com.kelvinfocus.chlorofillreminder.ui.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

abstract class BaseNotification {
    protected abstract fun builder(context: Context): NotificationCompat.Builder

    fun showNotification(context: Context, notificationId: Int) {
        val notificationBuilder = builder(context)
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notificationBuilder.build())
        }
    }
}