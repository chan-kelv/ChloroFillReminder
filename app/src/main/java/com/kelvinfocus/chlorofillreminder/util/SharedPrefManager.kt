package com.kelvinfocus.chlorofillreminder.util

import android.content.Context
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val sharedPref = getDefaultSharedPreferences(context)

    fun saveToDefaultPref(key: String, value: Int) {
        val edit = sharedPref.edit()
        edit.putInt(key, value).apply()
    }

    fun getFromDefaultPref(key: String, default: Int): Int {
        return sharedPref.getInt(key, default)
    }
}