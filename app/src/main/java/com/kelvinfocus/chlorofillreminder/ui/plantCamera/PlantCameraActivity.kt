package com.kelvinfocus.chlorofillreminder.ui.plantCamera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.camerakit.CameraKit
import com.kelvinfocus.chlorofillreminder.databinding.ActivityPlantCameraBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class PlantCameraActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPlantCameraBinding

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

        binding.takePhotoButton.setOnClickListener {
            binding.cameraView.captureImage { _ , capturedImage ->
                val imageString = Base64.encodeToString(capturedImage, Base64.DEFAULT)

                val bmp = BitmapFactory.decodeByteArray(capturedImage, 0, capturedImage.size)
                val stream = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.JPEG, imageCompression, stream)
                val compressedImageArray = stream.toByteArray()
                val compressedImageString = Base64.encodeToString(compressedImageArray, Base64.DEFAULT)

                val returnIntent = Intent()
                returnIntent.putExtra(IMAGE_KEY, compressedImageString)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }

        setCameraProperties()
        imageCompression = intent.getIntExtra(CAMERA_COMPRESSION_KEY, 100)
    }

    override fun onStart() {
        super.onStart()
        binding.cameraView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.cameraView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.cameraView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.cameraView.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        binding.cameraView.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setCameraProperties() {
        binding.cameraView.keepScreenOn = true
        binding.cameraView.flash = CameraKit.FLASH_OFF
        binding.cameraView.facing = CameraKit.FACING_BACK
        binding.cameraView.aspectRatio = 1f
        binding.cameraView.imageMegaPixels = 2f

    }

    companion object {
        const val CAMERA_COMPRESSION_KEY = "camera_compression_intent_key"
        const val IMAGE_KEY = "plant_image_intent_key"
    }
}