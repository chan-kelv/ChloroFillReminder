package com.kelvinfocus.chlorofillreminder.hilt

import android.content.Context
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
}