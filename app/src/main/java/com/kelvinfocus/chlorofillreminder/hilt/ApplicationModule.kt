package com.kelvinfocus.chlorofillreminder.hilt

import android.app.NotificationChannel
import android.content.Context
import com.kelvinfocus.chlorofillreminder.ui.notification.PlantNotification
import com.kelvinfocus.chlorofillreminder.util.SharedPrefManager
import com.kelvinfocus.chlorofillreminder.util.TextResUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Singleton
    @Provides
    fun providesTextResUtil(@ApplicationContext context: Context) = TextResUtil(context)

    @Singleton
    @Provides
    fun providesSharedPrefManager(@ApplicationContext context: Context) = SharedPrefManager(context)

    @Singleton
    @Provides
    fun providesNotificationChannelsToRegister(): List<NotificationChannel> = listOfNotNull(
        PlantNotification.channelToRegister()
    )
}