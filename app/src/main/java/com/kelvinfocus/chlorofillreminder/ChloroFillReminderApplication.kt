package com.kelvinfocus.chlorofillreminder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ChloroFillReminderApplication: Application() {

//    @Inject lateinit var notificationChannels: List<NotificationChannel>

    override fun onCreate() {
        super.onCreate()

        // Timber logging root
        Timber.plant(Timber.DebugTree())

        // Notification register (note: safe to call repeatedly)
        registerNotificationChannels()
    }

    private fun registerNotificationChannels() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            for (channel in notificationChannels) {
//                notificationManager.createNotificationChannel(channel)
//            }
//        }
    }
}