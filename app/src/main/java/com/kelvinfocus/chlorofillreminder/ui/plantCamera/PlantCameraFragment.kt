package com.kelvinfocus.chlorofillreminder.ui.plantCamera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kelvinfocus.chlorofillreminder.databinding.FragmentPlantCameraBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PlantCameraFragment: Fragment() {
    private lateinit var binding: FragmentPlantCameraBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantCameraBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.takePhotoButton.setOnClickListener {
            binding.cameraView.captureImage { cameraKitView, capturedImage ->
                Timber.d("Image: $capturedImage")
            }
        }

        return view
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