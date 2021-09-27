package com.kelvinfocus.chlorofillreminder.ui.plantCamera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.kelvinfocus.chlorofillreminder.databinding.ActivityPlantCameraBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PlantCameraActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPlantCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantCameraBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.takePhotoButton.setOnClickListener {
            binding.cameraView.captureImage { cameraKitView, capturedImage ->
                val imageString = Base64.encodeToString(capturedImage, Base64.DEFAULT)
                Timber.d("Image size: ${imageString.length}")

//                val bmp = BitmapFactory.decodeByteArray(capturedImage, 0, capturedImage.size)
//                val stream = ByteArrayOutputStream()
//                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                val compressedImageArray = stream.toByteArray()
//                val compressedImageString = Base64.encodeToString(compressedImageArray, Base64.DEFAULT)

//                Timber.d("Image compare: ${imageString.length} to compressed: ${compressedImageString.length}")
                val returnIntent = Intent()
                returnIntent.putExtra("image_data", imageString)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
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
}