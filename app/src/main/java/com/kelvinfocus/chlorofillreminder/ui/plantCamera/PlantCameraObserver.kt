package com.kelvinfocus.chlorofillreminder.ui.plantCamera

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject

class PlantCameraObserver @Inject constructor(
    private val registery: ActivityResultRegistry
): DefaultLifecycleObserver {
    lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
//        getContent = registery.register("key", owner, ActivityResultContracts.GetContent()) {
//
//        }
    }
}