package com.kelvinfocus.chlorofillreminder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ChloroFillReminderApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Timber logging root
        Timber.plant(Timber.DebugTree())
    }
}