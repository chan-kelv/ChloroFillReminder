package com.kelvinfocus.chlorofillreminder.ui.notification

import android.app.NotificationChannel

/**
 * Add to companion object of every notification channel and add to application module
 */
interface NotificationRegister {
    fun channelToRegister(): NotificationChannel?

}