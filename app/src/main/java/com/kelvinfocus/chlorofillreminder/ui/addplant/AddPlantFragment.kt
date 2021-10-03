package com.kelvinfocus.chlorofillreminder.ui.addplant

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kelvinfocus.chlorofillreminder.R
import com.kelvinfocus.chlorofillreminder.data.model.TimeFrequencyActionUnit
import com.kelvinfocus.chlorofillreminder.data.model.TimeIntervals
import com.kelvinfocus.chlorofillreminder.databinding.FragmentAddPlantBinding
import com.kelvinfocus.chlorofillreminder.ui.plantCamera.PlantCameraActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPlantFragment : Fragment() {
    private lateinit var binding: FragmentAddPlantBinding
    private lateinit var addPlantVM: AddPlantViewModel

    /* Must start before onStart I think? */
    lateinit var cameraResultsIntentListener: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPlantBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        addPlantVM = ViewModelProvider(this).get(AddPlantViewModel::class.java)

        setupViewData() // remaining view properties to set
        setupButtonHandlers() // button handlers
        setupVmHandlers() // vm observables

        setupCameraListener()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraResultsIntentListener.unregister()
    }

    private fun setupButtonHandlers() {
        binding.plantPhoto.setOnClickListener {
            val intent = Intent(activity, PlantCameraActivity::class.java)
            intent.putExtra(PlantCameraActivity.CAMERA_COMPRESSION_KEY, 25)
            cameraResultsIntentListener.launch(intent)
        }

        binding.saveNewPlantButton.setOnClickListener {
            addPlantVM.newPlantValidCheck(
                binding.waterAmountOfTimeInput.text.toString()
            )
        }
    }

    private fun setupCameraListener() {
        cameraResultsIntentListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    data.extras?.getString(PlantCameraActivity.PLANT_IMAGE_INTENT_DATA)?.let { imgString ->
                        val imgArray = Base64.decode(imgString, Base64.DEFAULT)
                        val imgBmp = BitmapFactory.decodeByteArray(imgArray, 0, imgArray.size)

                        activity?.runOnUiThread {
                            binding.plantPhoto.setImageBitmap(imgBmp)
                        }
                    }
                }
            }
        }
    }

    private fun setupViewData() {
        setTimeFrequencyUnitSpinners()
        setTimeIntervalSpinners()
    }

    private fun setTimeIntervalSpinners() {
        binding.waterFrequencyAlarm.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                TimeIntervals.generateValidTimeIntervals()
            )
        )
        binding.waterFrequencyAlarm.setHint(R.string.no_alarm)

        binding.fertilizerFrequencyAlarm.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                TimeIntervals.generateValidTimeIntervals()
            )
        )
        binding.fertilizerFrequencyAlarm.setHint(R.string.no_alarm)
    }

    private fun setTimeFrequencyUnitSpinners() {
        val timeIntervals = TimeFrequencyActionUnit.getAsList()
        binding.waterFrequencyUnit.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                timeIntervals
            )
        )
        binding.waterFrequencyUnit.setText(timeIntervals[0])

        binding.fertilizerFrequencyUnit.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                timeIntervals
            )
        )
        binding.fertilizerFrequencyUnit.setHint(R.string.no_time_unit)
    }

//    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            result.data?.let { data ->
//                Timber.d("Data: ${data.extras?.getString("image_data")?.length ?: 0}")
//            }
//        }
//    }


    private fun setupVmHandlers() {
        addPlantVM.waterScheduleInvalid.observe(viewLifecycleOwner) { invalidWaterFreq ->
            if (invalidWaterFreq) {
                binding.waterAmountOfTimeInput.error = "Invalid number"
            }
        }

        addPlantVM.newPlantValid.observe(viewLifecycleOwner) { isValidPlant ->
            if (isValidPlant) {
                addPlantVM.savePlant(
                    binding.plantPhoto,
                    binding.plantName.text.toString(),
                    binding.waterAmountOfTimeInput.text.toString(),
                    binding.waterFrequencyUnit.text.toString(),
                    binding.waterFrequencyAlarm.text.toString(),
                    binding.fertilizeAmountOfTimesInput.text.toString(),
                    binding.fertilizerFrequencyUnit.text.toString(),
                    binding.fertilizerFrequencyAlarm.text.toString(),
                    binding.plantNotes.text.toString(),
                )
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): AddPlantFragment {
            return AddPlantFragment()
        }
    }
}