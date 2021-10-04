package com.kelvinfocus.chlorofillreminder.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.kelvinfocus.chlorofillreminder.R
import com.kelvinfocus.chlorofillreminder.ui.MainActivity
import javax.inject.Inject

class PlantNotification @Inject constructor(): BaseNotification() {

    override fun builder(context: Context): NotificationCompat.Builder {
        val openToMainIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.putExtras(Bundle().apply {
                this.putBoolean("BACK_TO_ROOT_FRAG", true)
            })
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, openToMainIntent, 0)
        return NotificationCompat.Builder(context, PLANT_NOTIFICATION_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("Ferny is thirsty!")
            .setContentText("It is time to water Ferny!")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setChannelId(PLANT_CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // close notification when tapped
    }

    companion object: NotificationRegister {
        const val PLANT_NOTIFICATION_ID = "notification_plant_reminder"
        const val PLANT_CHANNEL_ID = "channel_plant_reminder"
        const val PLANT_CHANNEL_NAME = "Water Notification"
        override fun channelToRegister(): NotificationChannel? {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel =
                    NotificationChannel(PLANT_CHANNEL_ID, PLANT_CHANNEL_NAME, importance).apply {
                        description = "water plant reminder"
                        enableVibration(true)
                    }
                return channel
            }
            return null
        }
    }
}