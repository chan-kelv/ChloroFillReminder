package com.kelvinfocus.chlorofillreminder.ui.plantCamera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.camerakit.CameraKitView
import com.kelvinfocus.chlorofillreminder.databinding.ActivityPlantCameraBinding
import com.kelvinfocus.chlorofillreminder.ui.base.BaseCameraActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantCameraActivity: BaseCameraActivity() {
    private lateinit var binding: ActivityPlantCameraBinding

    override var cameraView: CameraKitView
        get() = binding.cameraView
        set(value) {}

    /*
        Compresses the camera image
        Range MUST be between 0-100 (100 actually can't be passed in intent because of size)
     */
    private var imageCompression = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantCameraBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageCompression = intent.getIntExtra(CAMERA_COMPRESSION_KEY, imageCompression)

        binding.takePhotoButton.setOnClickListener {
            super.captureImageAsBase64String(imageCompression, Bitmap.CompressFormat.JPEG) { imageString ->
                val returnIntent = Intent()
                returnIntent.putExtra(PLANT_IMAGE_INTENT_DATA, imageString)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }

    companion object {
        const val CAMERA_COMPRESSION_KEY = "camera_compression_intent_key"
        const val PLANT_IMAGE_INTENT_DATA = "plant_image_intent_key"
    }
}