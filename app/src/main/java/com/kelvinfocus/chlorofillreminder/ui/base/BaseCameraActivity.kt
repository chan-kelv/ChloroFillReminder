package com.kelvinfocus.chlorofillreminder.ui.base

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.camerakit.CameraKit
import com.camerakit.CameraKitView
import java.io.ByteArrayOutputStream

/**
 * Add to any activity that requires CameraKitView. Contains basic setup for camera kit activity
 */
abstract class BaseCameraActivity: AppCompatActivity() {
    abstract var cameraView: CameraKitView

    /**
     * Can override the properties based off of https://camerakit.io/docs#attributes
     */
    open fun setCameraProperties() {
        cameraView.keepScreenOn = true
        cameraView.flash = CameraKit.FLASH_OFF
        cameraView.facing = CameraKit.FACING_BACK
        cameraView.aspectRatio = 1f
        cameraView.imageMegaPixels = 2f
    }

    override fun onStart() {
        super.onStart()
        setCameraProperties()
        cameraView.onStart()
    }

    override fun onResume() {
        super.onResume()
        cameraView.onResume()
    }

    override fun onPause() {
        super.onPause()
        cameraView.onPause()
    }

    override fun onStop() {
        super.onStop()
        cameraView.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraView.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun captureImageAsBase64String(
        compressionRate: Int,
        compressionFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        captureComplete:(imgString: String) -> Unit
    ) {
        val validCompressionRate = if (compressionRate < 0 || compressionRate > 100) 100 else compressionRate
        cameraView.captureImage() { _ , capturedImage ->
            val imageString = Base64.encodeToString(capturedImage, Base64.DEFAULT)

            val bmp = BitmapFactory.decodeByteArray(capturedImage, 0, capturedImage.size)
            val stream = ByteArrayOutputStream()
            bmp.compress(compressionFormat, validCompressionRate, stream)
            val compressedImageArray = stream.toByteArray()
            val compressedImageString = Base64.encodeToString(compressedImageArray, Base64.DEFAULT)

            captureComplete.invoke(compressedImageString)
        }
    }
}