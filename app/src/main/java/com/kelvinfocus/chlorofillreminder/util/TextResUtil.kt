package com.kelvinfocus.chlorofillreminder.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TextResUtil @Inject constructor(
    @ApplicationContext val context: Context
) {
    fun getStringFromRes(stringRes: Int): String? = context.getString(stringRes)
}